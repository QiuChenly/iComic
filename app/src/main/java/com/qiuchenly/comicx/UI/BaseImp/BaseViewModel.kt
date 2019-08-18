package com.qiuchenly.comicx.UI.BaseImp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel<T> : Callback<T> {

    private var TAG = "BaseViewModel"
    override fun onFailure(call: Call<T>, t: Throwable) {
        loadFailure(t)
    }

    abstract fun loadFailure(t: Throwable)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!cancel)
            loadSuccess(call, response)
    }

    private var cancel = false
    abstract fun loadSuccess(call: Call<T>, response: Response<T>)
    open fun cancel() {
        cancel = true
    }

    fun Call<T>.getUrl(): String {
        return request().url().toString()
    }
}