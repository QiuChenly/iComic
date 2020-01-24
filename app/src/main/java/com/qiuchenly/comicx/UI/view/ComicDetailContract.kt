package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface ComicDetailContract {
    interface View : BaseView {
        fun scrollWithPosition(position: Int)
        fun onProgressChanged()
        fun onAppBarChange(position: Int)
    }

    interface Comiclist {
        interface View : BaseView {
            fun SetBikaPages(docs: ComicEpisodeResponse?)
            fun SetDMZJChapter(docs: ComicHomeComicChapterList)
            fun loadFailure(t: Throwable)
        }
    }
}