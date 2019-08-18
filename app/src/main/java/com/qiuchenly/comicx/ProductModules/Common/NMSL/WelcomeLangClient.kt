package com.qiuchenly.comicx.ProductModules.Common.NMSL

import com.qiuchenly.comicx.HttpRequests.BaseRetrofitManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WelcomeLangClient : BaseRetrofitManager<WelcomeLangApi>() {
    private const val BASE_URL_HAOCI = "https://v1.hitokoto.cn/"
    private fun generateAPI(base: String) {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            chain.proceed(
                original.newBuilder()
                    .header("X-Requested-With", "XMLHttpRequest")
                    .method(original.method(), original.body())
                    .build()
            )
        }
        //val tlsSocketFactory = TLSSocketFactory()
        //httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
        val api =
            Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<WelcomeLangApi>(
                WelcomeLangApi::class.java
            ) as WelcomeLangApi
        setAPI(api)
    }

    fun generateNiceLang() {
        generateAPI(BASE_URL_HAOCI)
    }
    //  https://v1.hitokoto.cn
    //  /?encode=json
}