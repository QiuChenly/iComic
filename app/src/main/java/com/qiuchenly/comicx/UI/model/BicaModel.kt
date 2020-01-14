package com.qiuchenly.comicx.UI.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.*
import com.qiuchenly.comicx.ProductModules.Bika.responses.*
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListResponse
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class BicaModel : ViewModel() {

    private var mBicaToken = ""

    var api: ApiService? = null

    var mRealm: WeakReference<Realm?>? = WeakReference(Comic.getRealm())

    private var mLintMessage = MutableLiveData<String>()
    var mLint: LiveData<String> = mLintMessage

    private var mDNSIP = MutableLiveData<Set<String>>()
    var mDNS: LiveData<Set<String>> = mDNSIP

    private var mUserProfile = MutableLiveData<UserProfileObject>()
    var mUserFile: LiveData<UserProfileObject> = mUserProfile

    private var mFavComicList = MutableLiveData<ComicListData>()
    var mFavItem: LiveData<ComicListData> = mFavComicList

    private var mCategoryList = MutableLiveData<ArrayList<CategoryObject>>()
    var mCategory: LiveData<ArrayList<CategoryObject>> = mCategoryList

    private var mRecentlySize = MutableLiveData<Int>()
    var mRecentSize: LiveData<Int> = mRecentlySize


    fun needLogin(): Boolean {
        mBicaToken = PreferenceHelper.getToken(Comic.getContext())
        return mBicaToken == ""
    }

    fun initBicaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(
            object : Callback<WakaInitResponse> {
                override fun onResponse(
                    call: Call<WakaInitResponse>,
                    response: Response<WakaInitResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.addresses != null &&
                            response.body()!!.addresses.size > 0
                        ) {
                            mDNSIP.value = HashSet(response.body()!!.addresses)
                            PreferenceHelper.setDnsIp(
                                Comic.getContext(),
                                HashSet(response.body()!!.addresses)
                            )
                            api = BikaApi.getAPI()
//                            mViews?.initSuccess()
                        } else {
                            mLintMessage.value = "哔咔服务器的CDN地址没有返回!"
                        }
                    } else {
                        mLintMessage.value = "初始化哔咔API失败。"
                    }
                }

                override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                    mLintMessage.value = "访问哔咔CDN服务器失败：" + t.message
                }
            })
    }

    fun updateUserInfo() {
        api?.getUserProfile(mBicaToken)
            ?.enqueue(object : Callback<GeneralResponse<UserProfileResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<UserProfileResponse>>,
                    t: Throwable
                ) {
                    mLintMessage.value = t.cause?.message ?: "获取用户信息错误!"
                }

                override fun onResponse(
                    call: Call<GeneralResponse<UserProfileResponse>>,
                    response: Response<GeneralResponse<UserProfileResponse>>
                ) {
                    val ret = response.body()?.data?.user
                    if (ret != null) {
                        mUserProfile.value = ret
//                        mViews?.updateUser(ret)
                        getFav {
                            mFavComicList.value = it
                        }
                        getBikaRecentlySize()
                    } else {
                        mLintMessage.value = "账户信息错误!"
                    }
                }
            })
    }

    fun initImage(callback: () -> Unit) {
        api?.getInit(PreferenceHelper.getToken(Comic.getContext()))
            ?.enqueue(object : Callback<GeneralResponse<InitialResponse>> {
                override fun onResponse(
                    call: Call<GeneralResponse<InitialResponse>>,
                    response: Response<GeneralResponse<InitialResponse>>
                ) {
                    when {
                        response.code() == 200 -> {
                            val imageServer = response.body()?.data?.imageServer
                            if (imageServer != null && imageServer.isNotEmpty()) {
                                PreferenceHelper.setImageStorage(Comic.getContext(), imageServer)
                            }
                            callback()
                        }
                        response.code() == 401 -> mLintMessage.value = "授权认证失败!需要登录哔咔!"
                        else -> mLintMessage.value =
                            "哔咔服务器返回错误数据:" + response.raw().body()?.string()
                    }
                }

                override fun onFailure(call: Call<GeneralResponse<InitialResponse>>, t: Throwable) {
                    mLintMessage.value = "完啦,bika图片服务器炸了."
                }
            })
    }

    fun punchSign(callback: (ret: Boolean) -> Unit) {
        api?.punchIn(mBicaToken)?.enqueue(object : Callback<GeneralResponse<PunchInResponse>> {
            override fun onFailure(call: Call<GeneralResponse<PunchInResponse>>, t: Throwable) {
                mLintMessage.value = t.cause?.message ?: "签到失败!"
            }

            override fun onResponse(
                call: Call<GeneralResponse<PunchInResponse>>,
                response: Response<GeneralResponse<PunchInResponse>>
            ) {
                val ret = response.body()?.data?.res?.status
                if (ret != null) {
                    callback(ret == "ok")
                } else {
                    mLintMessage.value = "签到异常!"
                }
            }
        })
    }

    fun getFav(callback: (list: ComicListData) -> Unit) {
        api?.getFavourite(mBicaToken, 1)
            ?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
                override fun onResponse(
                    call: Call<GeneralResponse<ComicListResponse>>,
                    response: Response<GeneralResponse<ComicListResponse>>
                ) {
                    if (response.body()?.data?.comics != null) {
                        callback(response.body()?.data?.comics!!)
                    } else {
                        mLintMessage.value = "没有拿到喜爱漫画数据。"
                    }
                }

                override fun onFailure(
                    call: Call<GeneralResponse<ComicListResponse>>,
                    t: Throwable
                ) {
                    mLintMessage.value = "获取收藏数据失败!"
                }
            })
    }

    fun getCategory() {
        api?.getCategories(mBicaToken)?.enqueue(
            object : Callback<GeneralResponse<CategoryResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<CategoryResponse>>,
                    t: Throwable
                ) {
                    mLintMessage.value = t.cause?.message ?: "获取哔咔漫画类别失败!"
                }

                override fun onResponse(
                    call: Call<GeneralResponse<CategoryResponse>>,
                    response: Response<GeneralResponse<CategoryResponse>>
                ) {
                    val mBikaCategoryArr = response.body()?.data?.getCategories()
                    if (mBikaCategoryArr != null) {
                        mBikaCategoryArr.add(
                            0,
                            CategoryObject("lastUpdate", "最近更新", "", mBikaCategoryArr[0].thumb)
                        )
                        mBikaCategoryArr.add(
                            0,
                            CategoryObject("random", "随机本子", "", mBikaCategoryArr[0].thumb)
                        )
                    }//哔咔搞什么鬼，返回了所有包括广告的类别 晕死
                    PreferenceHelper.setLocalApiDataCategoryList(
                        Comic.getContext(),
                        Gson().toJson(mBikaCategoryArr)
                    )
                    mCategoryList.value = mBikaCategoryArr
//                    mViews?.loadCategory(mBikaCategoryArr)
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
        mRecentlySize.value = size
//        mViews?.setRecentlyRead(size)
    }

    fun cancel() {
        mRealm = null
    }
}