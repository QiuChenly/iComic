package com.qiuchenly.comicx.UI.model

import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.UI.view.WeekContract
import io.realm.Realm
import io.realm.Sort
import java.lang.ref.WeakReference

class RecentlyModel(mView: WeekContract.View?) {
    var realm: WeakReference<Realm?>? = null

    init {
        realm = WeakReference(Comic.getRealm())
    }

    /**
     * 获取所有漫画源的最近阅读数据
     */
    fun getAllRecently(): ArrayList<RecentlyReadingBean> {
        val list = realm?.get()?.where(RecentlyReadingBean::class.java)
            ?.findAll()?.sort("mComicLastReadTime", Sort.DESCENDING) ?: return ArrayList()
        return ArrayList(list.toList())
    }

    /**
     * 获取指定漫画源的最近阅读数据
     */
    fun getTargetRecently(mSource: Int): ArrayList<RecentlyReadingBean> {
        val list = realm?.get()?.where(RecentlyReadingBean::class.java)
            ?.equalTo("mComicType", mSource)
            ?.findAll()?.sort("mComicLastReadTime", Sort.DESCENDING) ?: return ArrayList()
        return ArrayList(list.toList())
    }
}