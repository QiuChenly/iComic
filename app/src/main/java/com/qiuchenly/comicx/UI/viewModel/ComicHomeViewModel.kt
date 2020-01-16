package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHomeCategory
import com.qiuchenly.comicx.Bean.HotComic
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicHomeViewModel : BaseModel() {
    private var mCategory = MutableLiveData<ArrayList<ComicHomeCategory>>()
    var mCategoryBean: LiveData<ArrayList<ComicHomeCategory>> = mCategory
    private var mRecommend = MutableLiveData<ArrayList<ComicComm>>()
    var mmRecommendBean: LiveData<ArrayList<ComicComm>> = mRecommend
    private var mHotComic = MutableLiveData<HotComic>()
    var mHotComicBean: LiveData<HotComic> = mHotComic


    fun getDMZJCategory() {
        val mCall = DongManZhiJia.getV3API().category
        mCall.enqueue(object : Callback<ArrayList<ComicHomeCategory>> {
            override fun onFailure(call: Call<ArrayList<ComicHomeCategory>>, t: Throwable) {
                setError("加载动漫之家的类别数据失败!")
            }

            override fun onResponse(
                call: Call<ArrayList<ComicHomeCategory>>,
                response: Response<ArrayList<ComicHomeCategory>>
            ) {
                mCategory.value = response.body() ?: return
            }
        })
    }

    fun getDMZJRecommend() {
        val mCall = DongManZhiJia.getV3API().recommend
        mCall.enqueue(object : Callback<ArrayList<ComicComm>> {
            override fun onFailure(call: Call<ArrayList<ComicComm>>, t: Throwable) {
                setError("加载动漫之家的推荐数据失败!")
            }

            override fun onResponse(call: Call<ArrayList<ComicComm>>, response: Response<ArrayList<ComicComm>>) {
                mRecommend.value = response.body() ?: return
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
                setError("加载动漫之家的热门漫画数据失败!")
            }

            override fun onResponse(call: Call<HotComic>, response: Response<HotComic>) {
                mHotComic.value = response.body() ?: return
            }
        })
    }
}