package com.qiuchenly.comicx.UI.viewModel

import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHomeCategory
import com.qiuchenly.comicx.Bean.HotComic
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ComicHomeContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicHomeViewModel(Views: ComicHomeContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun loadFailure(t: Throwable) {
        mView?.OnNetFailed(t.message)
    }

    private var mView = Views

    private var mCall: Call<ResponseBody>? = null

    fun getDMZJCategory() {
        val mCall = DongManZhiJia.getV3API().category
        mCall.enqueue(object : Callback<ArrayList<ComicHomeCategory>> {
            override fun onFailure(call: Call<ArrayList<ComicHomeCategory>>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的类别数据失败!"))
            }

            override fun onResponse(
                call: Call<ArrayList<ComicHomeCategory>>,
                response: Response<ArrayList<ComicHomeCategory>>
            ) {
                response.body() ?: return
                mView?.onGetDMZJCategory(response.body()!!)
            }
        })
    }

    fun getDMZJRecommend() {
        val mCall = DongManZhiJia.getV3API().recommend
        mCall.enqueue(object : Callback<ArrayList<ComicComm>> {
            override fun onFailure(call: Call<ArrayList<ComicComm>>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的推荐数据失败!"))
            }

            override fun onResponse(call: Call<ArrayList<ComicComm>>, response: Response<ArrayList<ComicComm>>) {
                val ret = response.body() ?: return
                mView?.onGetDMZRecommendSuch(ret)
                getDMZJCategory()
            }
        })
    }

    /**
     * 获取热门漫画推荐
     * 热门连载:54 猜你喜欢:50 国漫也精彩:52
     */
    fun getDMZJHot(cate: Int) {
        val time: Int = (System.currentTimeMillis() / 1000).toInt()
        val mCall = DongManZhiJia.getV3API().getComicByType(time, cate)
        mCall.enqueue(object : Callback<HotComic> {
            override fun onFailure(call: Call<HotComic>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的热门漫画数据失败!"))
            }

            override fun onResponse(call: Call<HotComic>, response: Response<HotComic>) {
                println(Gson().toJson(response.body()))
                mView?.onGetDMZJHOT(response.body())
            }
        })
    }

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
        mView = null
    }
}