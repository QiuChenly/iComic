package com.qiuchenly.comicx.HttpRequests

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface BingRequests {
    @GET("/")
    fun getImageSrc(): Call<ResponseBody>
}