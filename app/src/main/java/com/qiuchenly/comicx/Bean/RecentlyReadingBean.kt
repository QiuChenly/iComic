package com.qiuchenly.comicx.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * 最近阅读漫画类
 */
@RealmClass
open class RecentlyReadingBean : RealmObject() {
    /**
     * 漫画名称
     */
    @PrimaryKey
    var mComicName = ""
    /**
     * 漫画图片
     */
    var mComicImageUrl = ""
    /**
     * 漫画数据来源
     */
    var mComicType: Int = 0

    /**
     * 漫画附加数据
     */
    var mComicData = ""

    /**
     * 漫画最后阅读时间
     */
    var mComicLastReadTime: Long = -1

    /**
     * 最后阅读进度：int数字下标
     */
    var mComicReadProgress: Int = -1

    /**
     * 最后阅读进度：文本格式
     */
    var mComicReadProgressText = ""
}