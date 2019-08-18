package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicListViewModel(private val view: ComicDetailContract.Comiclist.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        view.loadFailure(t)
    }

    var mView: ComicDetailContract.Comiclist.View? = view
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun getComicList(id: String, page: Int) {
        BikaApi.getAPI()?.getComicEpisode(PreferenceHelper.getToken(Comic.getContext()), id, page)
            ?.enqueue(object : Callback<GeneralResponse<ComicEpisodeResponse>> {
                override fun onFailure(call: Call<GeneralResponse<ComicEpisodeResponse>>, t: Throwable) {
                    loadFailure(Throwable("加载漫画章节失败!"))
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicEpisodeResponse>>,
                    response: Response<GeneralResponse<ComicEpisodeResponse>>
                ) {
                    mView?.SetBikaPages(response.body()?.data, id)
                }
            })
    }

    fun getDMZJComicList(obj_id: String) {
        DongManZhiJia.getV3API().getComic(obj_id)
            .enqueue(object : Callback<ComicHomeComicChapterList> {
                override fun onFailure(call: Call<ComicHomeComicChapterList>, t: Throwable) {
                    loadFailure(Throwable("加载动漫之家漫画章节失败!"))
                }

                override fun onResponse(
                    call: Call<ComicHomeComicChapterList>,
                    response: Response<ComicHomeComicChapterList>
                ) {
                    if (response.body() != null)
                        mView?.SetDMZJChapter(response.body()!!)
                }
            })
    }
}