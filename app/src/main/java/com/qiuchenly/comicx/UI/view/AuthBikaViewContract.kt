package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface AuthBikaViewContract {

    interface View : BaseView {
        fun LoginSucc()
        fun LoginFailed()
    }
}