package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.view.AuthBikaViewContract
import com.qiuchenly.comicx.UI.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_bika_auth.*

class AuthBika : BaseApp(), AuthBikaViewContract.View {
    override fun LoginFailed() {
        loading.visibility = View.INVISIBLE
        info.visibility = View.VISIBLE

    }

    override fun LoginSucc() {
        finish()
    }

    override fun getLayoutID(): Int? {
        return R.layout.activity_bika_auth
    }


    var mViewModel: AuthViewModel? = null

    override fun getUISet(mSet: BaseApp.UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = AuthViewModel(this)

        mLoginBika.setOnClickListener {
            loading.visibility = View.VISIBLE
            info.visibility = View.INVISIBLE
            mViewModel?.loginBika(mBikaUser.getStr(), mBikaPass.getStr())
        }

        back_up.setOnClickListener { finish() }
    }


    private fun EditText.getStr() = text.toString()

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
    }
}