package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_bika_auth.*

class AuthBica : BaseApp() {
    private fun loginFailed() {
        loading.visibility = View.INVISIBLE
        info.visibility = View.VISIBLE
    }

    private fun loginSuccess() {
        setResult(1000)
        finish()
    }

    override fun getLayoutID() = R.layout.activity_bika_auth

    lateinit var mViewModel: AuthViewModel

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(1001)
        mViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        with(mViewModel) {
            message.observe(this@AuthBica, Observer {
                loginFailed()
                ShowErrorMsg(it)
            })

            loginData.observe(this@AuthBica, Observer {
                PreferenceHelper.setUserLoginEmail(this@AuthBica, it.username)
                PreferenceHelper.setUserLoginPassword(this@AuthBica, it.password)
                PreferenceHelper.setToken(this@AuthBica, it.token)
                loginSuccess()
            })
        }

        mLoginBika.setOnClickListener {
            loading.visibility = View.VISIBLE
            info.visibility = View.INVISIBLE
            mViewModel.loginBika(mBikaUser.getStr(), mBikaPass.getStr())
        }

        back_up.setOnClickListener { finish() }
    }


    private fun EditText.getStr() = text.toString()

}