package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.UI.BaseImp.BaseView
import io.realm.RealmResults

interface MyDetailsContract {
    interface View : BaseView {
        fun onSrcReady(img: String)
        fun setRecentlySize(size: Int)
        fun setLocateComic(realmResults: RealmResults<LocalFavoriteBean>?)
    }
}