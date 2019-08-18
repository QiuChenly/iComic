package com.qiuchenly.comicx.Bean

object ComicSource {
    //动漫之家
    const val DongManZhiJia = 0x01
    //腾讯漫画
    const val TencentComic = 0x02
    //哔咔漫画
    const val BikaComic = 0x03
    //哔哩哔哩漫画
    const val BilibiliComic = 0x04

    /**
     * 获取中文名称
     */
    fun getTypeName(type: Int): String {
        return when (type) {
            BikaComic -> "哔咔漫画"
            DongManZhiJia -> "动漫之家"
            BilibiliComic -> "哔哩哔哩漫画"
            TencentComic -> "腾讯漫画"
            else -> "未知漫画源"
        }
    }

    /**
     * 获取代号
     */
    fun getTypeName(type: String): Int {
        return when (type) {
            "哔咔漫画" -> BikaComic
            "动漫之家" -> DongManZhiJia
            "哔哩哔哩漫画" -> BilibiliComic
            "腾讯漫画" -> TencentComic
            else -> -1
        }
    }

    fun getAllSource(): ArrayList<String> {
        return arrayListOf("哔咔漫画", "动漫之家", "哔哩哔哩漫画", "腾讯漫画")
    }
}