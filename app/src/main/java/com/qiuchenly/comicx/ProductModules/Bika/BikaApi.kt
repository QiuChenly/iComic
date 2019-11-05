package com.qiuchenly.comicx.ProductModules.Bika

import android.content.Context
import android.util.Log
import com.qiuchenly.comicx.EncryptUtils.BiKaJni
import com.qiuchenly.comicx.HttpRequests.BaseRetrofitManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object BikaApi : BaseRetrofitManager<ApiService>() {

    private var TAG = "BikaApi"
    //-----------------------    bika   API  --------------------------
    private val API_KEY = "C69BAF41DA5ABD1FFEDC6D2FEA56B"
    private val BASE_URL_PIKA = "https://picaapi.picacomic.com/"
    private val CERT_URL = "picaapi.picacomic.com"
    private var buildVersion: String = "44"
    private var uuid: String = "defaultUuid"
    private var version: String = "2.2.1.2.3.3"
    private var channel: Int = 2

    fun setBiCaClient(context: Context) {
        val httpClient = OkHttpClient.Builder()
        //httpClient.hostnameVerifier(getHostnameVerifier())
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        if (PreferenceHelper.isGirl(context)) {
            httpClient.dns(HttpDns())
        }
        httpClient.connectTimeout(5, TimeUnit.SECONDS)
        httpClient.readTimeout(5, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val uid = UUID.randomUUID().toString().replace("-", "")
            val url = original.url().toString().replace(BASE_URL_PIKA, "")
            val time = ((System.currentTimeMillis() / 1000) + PreferenceHelper.getTimeDifference(context)).toString()
            val signature = BiKaJni.getStringCon(
                arrayOf(
                    BASE_URL_PIKA,
                    url,
                    time,
                    uid,
                    original.method(),
                    API_KEY,
                    version,
                    buildVersion
                )
            )
            channel = PreferenceHelper.getChannel(context)
//            <array name="setting_options_image_qualities">
//            <item>原圖</item>
//            <item>低</item>
//            <item>中</item>
//            <item>高</item>
//            </array>
//            <array name="setting_options_image_qualities_server">
//            <item>original</item>
//            <item>low</item>
//            <item>medium</item>
//            <item>high</item>
//            </array>
            val response = chain.proceed(
                original.newBuilder()
                    .header("api-key", API_KEY)
                    .header("accept", "application/vnd.picacomic.com.v1+json")
                    .header("app-channel", channel.toString())
                    .header(ChatroomActionInterface.TIME, time)
                    .header("nonce", uid)
                    .header("signature", signature)
                    .header("app-version", version)
                    .header("app-uuid", uuid)
                    .header("image-quality", "original")//哔咔服务器加载图片质量
                    .header("app-platform", "android")
                    .header("app-build-version", buildVersion)
                    .header("user-agent", "okhttp/3.8.1")
                    .method(original.method(), original.body())
                    .build()
            )
            val serverTime = response.headers().get("Server-Time")
            if (serverTime != null) {
                val diffTime = java.lang.Long.parseLong(serverTime) - System.currentTimeMillis() / 1000
                PreferenceHelper.setTimeDifference(context, diffTime)
            }
            response
        }
        try {
            //val tlsSocketFactory = TLSSocketFactory()
            //httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
            val api =
                Retrofit.Builder().baseUrl(BASE_URL_PIKA).addConverterFactory(GsonConverterFactory.create()).client(
                    httpClient.build()
                ).build().create<ApiService>(ApiService::class.java) as ApiService
            setAPI(api)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "setBiCaClient:API SSL 异常你来辣!")
        }
    }
}