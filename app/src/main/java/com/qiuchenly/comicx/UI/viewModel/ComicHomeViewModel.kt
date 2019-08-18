package com.qiuchenly.comicx.UI.viewModel

import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHome_Category
import com.qiuchenly.comicx.Bean.HotComic
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ComicHomeContract
import okhttp3.ResponseBody
import org.json.JSONArray
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
        mCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的类别数据失败!"))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val ret = response.body()?.string() ?: return
                val cls = JSONArray(ret)
                var size = 0
                val mArrs = ArrayList<ComicHome_Category>()
                while (size < cls.length() - 1) {
                    mArrs.add(Gson().fromJson(cls.getJSONObject(size).toString(), ComicHome_Category()::class.java))
                    size++
                }
                mView?.onGetDMZJCategory(mArrs)
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

    fun getDMZJHot() {
        val time: Int = (System.currentTimeMillis() / 1000).toInt()
        val mCall = DongManZhiJia.getV3API().getComicByHot(time)
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