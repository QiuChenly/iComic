package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface SearchContract {
    interface View : BaseView, GetPageCB {
        fun onKeysLoadSucc(arr: ArrayList<String>)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack
}