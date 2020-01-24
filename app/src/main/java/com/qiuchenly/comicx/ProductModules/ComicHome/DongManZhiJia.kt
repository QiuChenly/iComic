package com.qiuchenly.comicx.ProductModules.ComicHome

import com.qiuchenly.comicx.HttpRequests.BaseRetrofitManager
import com.qiuchenly.comicx.Utils.bikaUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DongManZhiJia : BaseRetrofitManager<ComicApi>() {
    fun getCusAPI(mApi: String): ComicApi {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        val api = Retrofit
            .Builder()
            .baseUrl(mApi)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(
                httpClient.build()
            )
            .build()
            .create<ComicApi>(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return api
    }

    //https://images.dmzj.com/img/webpic/15/1003681551458703419.jpg
    fun getUserAPI(): ComicApi {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        val api = Retrofit
            .Builder()
            .baseUrl(ComicApi.BASE_API_USER)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(
                httpClient.build()
            )
            .build()
            .create<ComicApi>(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return api
    }

    private var mV3API: ComicApi? = null
    fun getV3API(): ComicApi {
        mV3API = getAPI()
        if (mV3API != null) return mV3API!!
        val httpClient = OkHttpClient.Builder()
            .hostnameVerifier { hostname, session -> true }
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        bikaUtils.handleSSLHandshake(httpClient)
        val api = Retrofit
            .Builder()
            .baseUrl(ComicApi.BASE_API_V3API)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(
                httpClient.build()
            )
            .build()
            .create(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return getAPI()!!
    }
}