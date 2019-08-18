package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHome_Category
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

        fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
        fun onGetDMZJHOT(mComicCategory: HotComic?)
    }

    interface DMZJ_Adapter {
        fun addDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
        fun addDMZJData(mComicList: ArrayList<ComicComm>)
    }
}