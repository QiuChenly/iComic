package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.viewModel.AuthViewModel
import com.qiuchenly.comicx.databinding.ActivityBikaAuthBinding

class AuthBica : BaseApp() {
    private fun loginFailed() {
        mView.loading.visibility = View.INVISIBLE
        mView.info.visibility = View.VISIBLE
    }

    private fun loginSuccess() {
        setResult(1000)
        finish()
    }

    private lateinit var mView: ActivityBikaAuthBinding
    override fun getLayoutID(): View {
        //R.layout.activity_bika_auth
        mView = ActivityBikaAuthBinding.inflate(this.layoutInflater)
        return mView.root
    }

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

        mView.mLoginBika.setOnClickListener {
            mView.loading.visibility = View.VISIBLE
            mView.info.visibility = View.INVISIBLE
            mViewModel.loginBika(mView.mBikaUser.getStr(), mView.mBikaPass.getStr())
        }

        mView.backUp.setOnClickListener { finish() }
    }


    private fun EditText.getStr() = text.toString()

}