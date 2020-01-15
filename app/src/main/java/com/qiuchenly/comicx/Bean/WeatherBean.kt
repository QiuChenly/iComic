package com.qiuchenly.comicx.Bean

data class WeatherBean(
    val code: String, // A000000
    val `data`: Data,
    val message: String,
    val timestamp: String // 20200115190526
) {
    data class Data(
        val airNow: AirNow,
        val currentTime: String, // 20200115190526
        val location: Location,
        val weatherDaily: List<WeatherDaily>,
        val weatherNow: WeatherNow
    ) {
        data class AirNow(
            val aqi: String, // 40
            val co: String, // 0.325
            val lastUpdate: String, // 2020-01-15T18:00:00+08:00
            val no2: String, // 24
            val o3: String, // 65
            val pm10: String, // 40
            val pm25: String, // 11
            val quality: String, // 优
            val so2: String // 2
        )

        data class Location(
            val country: String, // CN
            val id: String, // WTVMWP4TCFT7
            val name: String, // 盐城
            val path: String, // 盐城,盐城,江苏,中国
            val timezone: String, // Asia/Shanghai
            val timezoneOffset: String // +08:00
        )

        data class WeatherDaily(
            val codeDay: String, // 0
            val codeNight: String, // 1
            val date: String, // 2020-01-19
            val high: String, // 10
            val imgDay: String, // http://weather.api.gitv.tv/img/180/0.png
            val imgNight: String, // http://weather.api.gitv.tv/img/180/1.png
            val low: String, // 0
            val textDay: String, // 晴
            val textNight: String, // 晴
            val weekDay: String // 周日
        )

        data class WeatherNow(
            val code: String, // 9
            val feelsLike: String, // 3
            val humidity: String, // 67
            val img: String, // http://weather.api.gitv.tv/img/180/9.png
            val pressure: String, // 1030
            val temperature: String, // 4
            val text: String, // 阴
            val visibility: String, // 1.4
            val windDirection: String, // 东
            val windScale: String, // 3
            val windSpeed: String // 16.0
        )
    }
}