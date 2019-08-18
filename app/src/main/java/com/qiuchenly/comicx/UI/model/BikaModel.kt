package com.qiuchenly.comicx.UI.model

import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.*
import com.qiuchenly.comicx.ProductModules.Bika.responses.*
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicx.UI.view.BikaInterface
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.util.*

class BikaModel(var mViews: BikaInterface?) {

    var mBikaToken = ""

    var api: ApiService? = null

    var mRealm: WeakReference<Realm?>? = WeakReference(Comic.getRealm())

    fun needLogin(): Boolean {
        mBikaToken = PreferenceHelper.getToken(Comic.getContext())
        return mBikaToken == ""
    }

    fun initBikaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(object : Callback<WakaInitResponse> {
            override fun onResponse(call: Call<WakaInitResponse>, response: Response<WakaInitResponse>) {
                if (response.code() == 200) {
                    if (response.body()?.addresses != null && response.body()!!.addresses.size > 0) {
                        PreferenceHelper.setDnsIp(Comic.getContext(), HashSet(response.body()!!.addresses))
                        BikaApi.setBiCaClient(Comic.getContext()!!)//fix that app can't login & request data for the first time
                        api = BikaApi.getAPI()
                        mViews?.initSuccess()
                    } else {
                        mViews?.ShowErrorMsg("哔咔服务器的CDN地址没有返回!")
                    }
                } else {
                    mViews?.ShowErrorMsg("初始化哔咔API失败。")
                }
            }

            override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                mViews?.ShowErrorMsg("访问哔咔CDN服务器失败：" + t.message)
            }
        })
    }

    fun updateUserInfo() {
        api?.getUserProfile(mBikaToken)?.enqueue(object : Callback<GeneralResponse<UserProfileResponse>> {
            override fun onFailure(call: Call<GeneralResponse<UserProfileResponse>>, t: Throwable) {
                mViews?.ShowErrorMsg(t.cause?.message ?: "获取用户信息错误!")
            }

            override fun onResponse(
                call: Call<GeneralResponse<UserProfileResponse>>,
                response: Response<GeneralResponse<UserProfileResponse>>
            ) {
                val ret = response.body()?.data?.user
                if (ret != null) {
                    mViews?.updateUser(ret)
                    getFav()
                    getBikaRecentlySize()
                } else {
                    mViews?.ShowErrorMsg("账户信息错误!")
                }
            }
        })
    }

    fun initImage() {
        api?.getInit(PreferenceHelper.getToken(Comic.getContext()))
            ?.enqueue(object : Callback<GeneralResponse<InitialResponse>> {
                override fun onResponse(
                    call: Call<GeneralResponse<InitialResponse>>,
                    response: Response<GeneralResponse<InitialResponse>>
                ) {
                    if (response.code() == 200) {
                        val imageServer = response.body()?.data?.imageServer
                        if (imageServer != null && imageServer.isNotEmpty()) {
                            PreferenceHelper.setImageStorage(Comic.getContext(), imageServer)
                        }
                        mViews?.initImageServerSuccess()
                    } else if (response.code() == 401) {
                        mViews?.ShowErrorMsg("授权认证失败!需要登录哔咔!")
                    } else mViews?.ShowErrorMsg("哔咔服务器返回错误数据:" + response.raw().body()?.string())
                }

                override fun onFailure(call: Call<GeneralResponse<InitialResponse>>, t: Throwable) {
                    mViews?.ShowErrorMsg("完啦,bika图片服务器炸了.")
                }
            })
    }

    fun punchSign() {
        api?.punchIn(mBikaToken)?.enqueue(object : Callback<GeneralResponse<PunchInResponse>> {
            override fun onFailure(call: Call<GeneralResponse<PunchInResponse>>, t: Throwable) {
                mViews?.ShowErrorMsg(t.cause?.message ?: "签到失败!")
            }

            override fun onResponse(
                call: Call<GeneralResponse<PunchInResponse>>,
                response: Response<GeneralResponse<PunchInResponse>>
            ) {
                val ret = response.body()?.data?.res?.status
                if (ret != null) {
                    mViews?.signResult(ret == "ok")
                } else {
                    mViews?.ShowErrorMsg("签到异常!")
                }
            }
        })
    }

    fun getFav() {
        api?.getFavourite(mBikaToken, 1)?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
            override fun onResponse(
                call: Call<GeneralResponse<ComicListResponse>>,
                response: Response<GeneralResponse<ComicListResponse>>
            ) {
                if (response.body()?.data?.comics != null) {
                    mViews?.getFavourite(response.body()?.data?.comics!!)
                } else {
                    mViews?.ShowErrorMsg("没有拿到喜爱漫画数据。")
                }
            }

            override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                mViews?.ShowErrorMsg("获取收藏数据失败!")
            }
        })
    }

    fun getCategory() {
        api?.getCategories(mBikaToken)?.enqueue(
            object : Callback<GeneralResponse<CategoryResponse>> {
                override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                    mViews?.ShowErrorMsg(t.cause?.message ?: "获取哔咔漫画类别失败!")
                }

                override fun onResponse(
                    call: Call<GeneralResponse<CategoryResponse>>,
                    response: Response<GeneralResponse<CategoryResponse>>
                ) {
                    val mBikaCategoryArr = response.body()?.data?.getCategories()
                    if (mBikaCategoryArr != null) {
                        mBikaCategoryArr.add(0, CategoryObject("lastUpdate", "最近更新", "", mBikaCategoryArr[0].thumb))
                        mBikaCategoryArr.add(0, CategoryObject("random", "随机本子", "", mBikaCategoryArr[0].thumb))
                    }//哔咔搞什么鬼，返回了所有包括广告的类别 晕死
                    PreferenceHelper.setLocalApiDataCategoryList(Comic.getContext(), Gson().toJson(mBikaCategoryArr))
                    mViews?.loadCategory(mBikaCategoryArr)
                }
            })
    }

    /**
     * 获取漫画源的最近阅读数据数量
     */
    fun getBikaRecentlySize() {
        val size = mRealm?.get()?.where(RecentlyReadingBean::class.java)
            ?.equalTo("mComicType", ComicSource.BikaComic)
            ?.findAll()?.size ?: 0
        mViews?.setRecentlyRead(size)
    }

    fun cancel() {
        mRealm = null
        mViews = null
    }
}