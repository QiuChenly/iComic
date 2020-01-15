package com.qiuchenly.comicx.HttpRequests

import com.qiuchenly.comicx.Bean.RandomImageBean
import com.qiuchenly.comicx.Bean.WeatherBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface WeatherRequest {
    @GET("/s?q=%E5%A4%A9%E6%B0%94")
    fun getWeatherInfo(): Call<ResponseBody>

    @GET("/v1/general/now")//http://weather.api.gitv.tv
    fun getWeatherByTV(): Call<WeatherBean>

    @GET("/random-wallpaper")
    fun getRandomBackgroung(): Call<RandomImageBean>//https://infinity-api.infinitynewtab.com
}