package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface ReaderContract {
    interface View : BaseView, GetPageCB

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack {
        fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String, isOver: Boolean)
    }
}