package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.UI.BaseImp.BaseLoadingView
import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface ComicDetailContract {
    interface View : BaseView, BaseLoadingView {
        fun scrollWithPosition(position: Int)
        fun onProgressChanged()
        fun onAppBarChange(position: Int)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetScore : BaseGetCallBack {
        fun getScoreSucc(rate: String)

    }

    interface GetPageInfo : BaseGetCallBack

    interface Comiclist {
        interface View : BaseView {
            fun SetBikaPages(docs: ComicEpisodeResponse?, id: String)
            fun SetDMZJChapter(docs: ComicHomeComicChapterList)
            fun loadFailure(t: Throwable)
        }
    }

    interface ComicInfo {
        interface View : BaseView {
            fun SetBikaInfo(comic: ComicDetailObject?)
        }
    }
}