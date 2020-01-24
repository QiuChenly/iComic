package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.KeywordsResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.SearchContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private var mView: SearchContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg(t.message!!)
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun getBikaKeyWords() {
        BikaApi.getAPI()?.getKeywords(PreferenceHelper.getToken(Comic.getContext()))
            ?.enqueue(object : Callback<GeneralResponse<KeywordsResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<KeywordsResponse>>,
                    t: Throwable
                ) {
                    loadFailure(Throwable("加载Bika关键词失败!"))
                }

                override fun onResponse(
                    call: Call<GeneralResponse<KeywordsResponse>>,
                    response: Response<GeneralResponse<KeywordsResponse>>
                ) {
                    mView?.onKeysLoadSucc(response.body()?.data?.keywords ?: return)
                }
            })
    }

    fun getComicHomeKeyWords() {
        DongManZhiJia.getV3API().getHotSearchKey(System.currentTimeMillis())
            ?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loadFailure(Throwable("加载漫画之家的关键词失败!"))
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    mView?.onKeysLoadSucc(arrayListOf(""))
                }
            })
    }
}