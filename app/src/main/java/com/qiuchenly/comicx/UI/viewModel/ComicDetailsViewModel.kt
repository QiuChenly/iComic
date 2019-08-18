package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ComicDetailsViewModel(private var callback: ComicDetailContract.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        callback.onLoadFailed()
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        callback.onLoadSuccess()
    }

    private var mCall: Call<ResponseBody>? = null

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
        mCall = null
    }
}