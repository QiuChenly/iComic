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
    @PrimaryKey
    var BookName = ""
    var Author = ""
    var DownOver = false
    var PageList = RealmList<PageInfo>()
    var Booklink = ""
}

/**
 * 章节名称和章节内漫画图片的集合
 */
open class PageInfo : RealmObject() {
    var titleName = ""
    var imageList = RealmList<ImageUrl>()
    var DownOver = false
}

/**
 * 保存图片的网址和实际本地地址
 */
open class ImageUrl : RealmObject() {
    var urlAddress = ""
    var localSaveAddress = ""
}