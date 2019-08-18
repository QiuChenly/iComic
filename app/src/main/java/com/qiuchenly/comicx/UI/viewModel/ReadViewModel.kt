package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Bean.ChapterList
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.Tools
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicPageResponse.ComicPagesResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ReaderContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadViewModel(private var mView: ReaderContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg(t.message!!)
    }

    /*
        var qTcms_S_m_name="指染成婚";
        var qTcms_S_m_playm="第145话 坐地起价与气极谋杀？！";
        var qTcms_Pic_nextArr="/comic/10452/372260.html";
    */
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun updateReadPoint(point: String, currentUrl: String) {
//        (AppManager.getActivity(MainActivity::class.java) as MainActivity).updateInfo()
    }

    fun getBikaImage(bookID: String?, order: Int) {
        BikaApi.getAPI()?.getPagesWithOrder(PreferenceHelper.getToken(Comic.getContext()), bookID, order, 1)
            ?.enqueue(object : Callback<GeneralResponse<ComicPagesResponse>> {
                override fun onFailure(call: Call<GeneralResponse<ComicPagesResponse>>, t: Throwable) {
                    mView?.onFailed("加载哔咔漫画章节失败!")
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicPagesResponse>>,
                    response: Response<GeneralResponse<ComicPagesResponse>>
                ) {
                    val arr = ArrayList<String>()
                    for (a in response.body()?.data?.pages?.docs!!) {
                        arr.add(Tools.getThumbnailImagePath(a.media))
                    }
                    mView?.onLoadSucc(arr, "", "", false)
                }
            })
    }

    fun getDMZJImage(bookID: String, chapter_id: String) {
        DongManZhiJia.getV3API().getComic(bookID, chapter_id)
            .enqueue(object : Callback<ChapterList> {
                override fun onFailure(call: Call<ChapterList>, t: Throwable) {
                    mView?.onFailed("加载动漫之家的漫画章节失败!")
                }

                override fun onResponse(call: Call<ChapterList>, response: Response<ChapterList>) {
                    if (response.body() != null)
                        mView?.onLoadSucc(
                            ArrayList(response.body()!!.page_url), "", response.body()?.title
                                ?: "NULL - 数据错误!", false
                        )
                }
            })
    }
}