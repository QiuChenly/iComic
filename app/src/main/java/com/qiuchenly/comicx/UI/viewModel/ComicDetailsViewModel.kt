package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.ComicDetailResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicDetailsViewModel : BaseModel() {

    private var mCall: Call<ResponseBody>? = null

    fun cancel() {
        if (mCall != null) mCall!!.cancel()
        mCall = null
        mRealm = null
    }

    private var mComicBicaList = MutableLiveData<ComicEpisodeResponse>()
    var mComicBicaChapterList: LiveData<ComicEpisodeResponse> = mComicBicaList


    private var bicaComicInfo = MutableLiveData<ComicDetailObject>()
    var mBicaComic: LiveData<ComicDetailObject> = bicaComicInfo

    private var mRealm = Comic.getRealm()


    /**
     * 获取动漫之家的漫画详情页数据
     */
    /**
     * 获取动漫之家的章节列表
     */
    fun getComicHomeComicChapter(comidID: String) {
        DongManZhiJia.getV3API().getComic(comidID)
            .enqueue(object : Callback<ComicHomeComicChapterList> {
                override fun onFailure(call: Call<ComicHomeComicChapterList>, t: Throwable) {
                    setError("动漫之家:加载数据失败!" + t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ComicHomeComicChapterList>,
                    response: Response<ComicHomeComicChapterList>
                ) {
                    response.body() ?: return
                    mComicHomeChapterList.value = response.body()
                }
            })
    }

    /**
     * 获取bica的章节列表
     */
    fun getComicList(id: String, page: Int) {
        BikaApi.getAPI()?.getComicEpisode(PreferenceHelper.getToken(Comic.getContext()), id, page)
            ?.enqueue(object : Callback<GeneralResponse<ComicEpisodeResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<ComicEpisodeResponse>>,
                    t: Throwable
                ) {
                    setError("加载漫画章节失败!")
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicEpisodeResponse>>,
                    response: Response<GeneralResponse<ComicEpisodeResponse>>
                ) {
                    response.body() ?: return
                    mComicBicaList.value = response.body()?.data
                }
            })
    }

    /**
     * 获取动漫之家章节
     */
    fun getDMZJComicList(obj_id: String) {
        DongManZhiJia.getV3API().getComic(obj_id)
            .enqueue(object : Callback<ComicHomeComicChapterList> {
                override fun onFailure(call: Call<ComicHomeComicChapterList>, t: Throwable) {
                    setError("加载动漫之家漫画章节失败!")
                }

                override fun onResponse(
                    call: Call<ComicHomeComicChapterList>,
                    response: Response<ComicHomeComicChapterList>
                ) {
                    response.body() ?: return
                    mComicHomeChapterList.value = response.body()
                }
            })
    }

    private var mComicHomeChapterList = MutableLiveData<ComicHomeComicChapterList>()
    var mComicHomeChapter: LiveData<ComicHomeComicChapterList> = mComicHomeChapterList

    fun getComicInfo(bookID: String?) {
        BikaApi.getAPI()?.getComicWithId(PreferenceHelper.getToken(Comic.getContext()), bookID)
            ?.enqueue(object : Callback<GeneralResponse<ComicDetailResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<ComicDetailResponse>>,
                    t: Throwable
                ) {
                    setError("网络请求失败!")
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicDetailResponse>>,
                    response: Response<GeneralResponse<ComicDetailResponse>>
                ) {
                    bicaComicInfo.value = response.body()?.data?.comic ?: return
                }
            })
    }

    fun comicExist(comicName: String): LocalFavoriteBean? {
        return mRealm?.where(LocalFavoriteBean::class.java)
            ?.equalTo("mComicName", comicName)
            ?.findFirst()
    }

    fun comicDel(book: String) {
        val data = mRealm?.where(LocalFavoriteBean::class.java)
            ?.equalTo("mComicName", book)
            ?.findFirst()
        mRealm?.beginTransaction()
        data?.deleteFromRealm()
        mRealm?.commitTransaction()
    }

    fun comicAdd(book: LocalFavoriteBean) {
        mRealm?.executeTransaction {
            it.copyToRealm(book)
        }
    }
}