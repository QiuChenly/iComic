package com.qiuchenly.comicx.Service

import com.qiuchenly.comicx.Bean.DownloadBookInfo
import com.qiuchenly.comicx.Bean.PageInfo
import io.realm.RealmResults

interface ServiceNotification {
    fun onMessage(title: String, content: String, NoticeID: Int)
    fun onSaveBookPage(mBookName: String, mPageInfo: PageInfo)
    fun onBookHasInDataBase(mBookName: String): Boolean
    fun onBookAdded(mDownloadBookInfo: DownloadBookInfo)
    fun onGetAllDownBook(): RealmResults<DownloadBookInfo>?
    fun onDownBookOver(bookName: String)
    fun onBookPageWasDown(bookName: String, title: String): Boolean
}