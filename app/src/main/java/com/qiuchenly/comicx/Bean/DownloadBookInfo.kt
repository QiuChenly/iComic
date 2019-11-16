package com.qiuchenly.comicx.Bean

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * 下载漫画书主类集合
 */
@RealmClass
open class DownloadBookInfo : RealmObject() {
    /**
     * 书名
     */
    @PrimaryKey
    var BookName = ""
    /**
     * 作者
     */
    var Author = ""
    /**
     * 下载状态
     */
    var DownOver = false
    /**
     * 章节列表
     */
    var PageList = RealmList<PageInfo>()
    /**
     * 就是哔咔漫画中的comicId
     */
    var Booklink = ""
    /**
     * 漫画图片
     */
    var BookImage = ""
    /**
     * 漫画类别
     */
    var BookCategory = RealmList<String>()
    /**
     * 漫画数据来源
     */
    var BookSource = ComicSource.BikaComic
}

/**
 * 章节名称和章节内漫画图片的集合
 */
open class PageInfo : RealmObject() {
    /**
     * 章节标题名称
     */
    var titleName = ""
    /**
     * 章节中所有的漫画图片列表
     */
    var imageList = RealmList<ImageUrl>()
    var DownOver = false
    /**
     * 本章节排序第几
     */
    var order = 0
    /**
     * 服务器上的章节ID
     */
    var chapterID = ""
}

/**
 * 保存图片的网址和实际本地地址
 */
open class ImageUrl : RealmObject() {
    /**
     * 实际网络地址
     */
    var urlAddress = ""
    /**
     * 本地图片地址
     */
    var localSaveAddress = ""
}