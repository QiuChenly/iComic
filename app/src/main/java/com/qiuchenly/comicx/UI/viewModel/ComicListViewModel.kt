package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicListViewModel : ViewModel() {

    private var mComicHomeChapterList = MutableLiveData<ComicHomeComicChapterList>()
    var mComicHomeList: LiveData<ComicHomeComicChapterList> = mComicHomeChapterList

    private var mComicBicaList = MutableLiveData<ComicEpisodeResponse>()
    var mComicBicaChapterList: LiveData<ComicEpisodeResponse> = mComicBicaList

    private var mLint = MutableLiveData<String>()
    var mLintMessage: LiveData<String> = mLint

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
                    mLint.value = "加载漫画章节失败!"
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
                    mLint.value = "加载动漫之家漫画章节失败!"
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
}