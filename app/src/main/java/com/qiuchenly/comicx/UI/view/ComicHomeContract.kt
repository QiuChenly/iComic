package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHomeCategory
import com.qiuchenly.comicx.Bean.HotComic
import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface ComicHomeContract {
    interface View : BaseView, DongManZhiJia {
        fun OnNetFailed(message: String?)
        fun goSelectSource()
        fun final()
    }

    interface DongManZhiJia {
        /**
         * 获得动漫之家的首页推荐数据
         */
        fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>)

        fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHomeCategory>)
        fun onGetDMZJHOT(mComicCategory: HotComic?)
    }

    interface DMZJ_Adapter {
        fun addDMZJCategory(mComicCategory: ArrayList<ComicHomeCategory>)
        fun addDMZJData(mComicList: ArrayList<ComicComm>)
    }
}