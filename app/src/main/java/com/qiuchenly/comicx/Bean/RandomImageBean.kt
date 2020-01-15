package com.qiuchenly.comicx.Bean

data class RandomImageBean(
    val `data`: List<Data>,
    val status: Int, // 200
    val success: Boolean // true
) {
    data class Data(
        val _id: String, // 5816f3e7df4f792e31cfb068
        val colors: List<String>,
        val dimensions: String, // 2509px × 1673px
        val from: String, // official
        val imgId: String, // 9037
        val license: License,
        val rate: Int, // 0
        val similarColors: List<String>,
        val source: String, // Unsplash
        val src: Src,
        val tags: List<String>
    ) {
        data class License(
            val breif: String, // CC0
            val name: String // Public Domain Dedication
        )

        data class Src(
            val bigSrc: String, // https://infinitypro-img.infinitynewtab.com/findaphoto/bigLink/9037.jpg?imageMogr2/auto-orient/blur/1x0/quality/71|imageslim
            val mediumSrc: String, // https://infinitypro-img.infinitynewtab.com/findaphoto/bigLink/9037.jpg?imageMogr2/auto-orient/thumbnail/1000x/blur/1x0/quality/75|imageslim
            val originalSrc: String, // https://infinitypro-img.infinitynewtab.com/findaphoto/bigLink/9037.jpg?imageMogr2/auto-orient/thumbnail/1000x/blur/1x0/quality/75|imageslim
            val rawSrc: String, // https://infinitypro-img.infinitynewtab.com/findaphoto/bigLink/9037.jpg
            val smallSrc: String // https://infinitypro-img.infinitynewtab.com/findaphoto/bigLink/9037.jpg?imageMogr2/auto-orient/thumbnail/600x/blur/1x0/quality/75|imageslim
        )
    }
}