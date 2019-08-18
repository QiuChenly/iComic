package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PreferenceViewModel : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    fun setBikaMode(bool: Boolean) {
        PreferenceHelper.setNoLoginBika(Comic.getContext(), !bool)
    }

    fun getBikaMode(): Boolean {
        return PreferenceHelper.getNoLoginBika(Comic.getContext())
    }
}