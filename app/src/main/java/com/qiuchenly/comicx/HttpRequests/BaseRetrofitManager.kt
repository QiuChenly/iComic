package com.qiuchenly.comicx.HttpRequests

import com.qiuchenly.comicx.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRetrofitManager<ApiType> {

    private var mAPI: ApiType? = null

    fun setAPI(mAPI: ApiType) {
        this.mAPI = mAPI
    }

    fun getAPI() = mAPI

    fun getCusUrl(BaseUrl: String, isJsonBody: Boolean = false) = buildRetrofit(BaseUrl, isJsonBody)
    private fun buildRetrofit(isJsonBody: Boolean = false): Retrofit.Builder {
        val mHttpInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        val mHttp = OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            addInterceptor(mHttpInterceptor)
        }.build()

        val a = Retrofit.Builder().apply {
            client(mHttp)
        }
        if (isJsonBody)
            a.addConverterFactory(GsonConverterFactory.create())
        return a
    }

    private fun buildRetrofit(mBaseUrl: String, isJsonBody: Boolean = false): Retrofit {
        return buildRetrofit(isJsonBody).baseUrl(mBaseUrl).build()
    }
}