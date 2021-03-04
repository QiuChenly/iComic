package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.View
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.viewModel.PreferenceViewModel
import com.qiuchenly.comicx.databinding.ActivityPreferBinding

class PerferenceActivity : BaseApp() {

    private lateinit var mView: ActivityPreferBinding
    var mViewModel: PreferenceViewModel? = null
    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    override fun getLayoutID(): View {
//        R.layout.activity_prefer
        mView = ActivityPreferBinding.inflate(layoutInflater)
        return mView.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = PreferenceViewModel()
        mView.useBikaSource.setOnClickListener {
            mViewModel?.setBikaMode(mView.useBikaSource.isChecked)
        }
        mView.backUp.setOnClickListener { finish() }
        initAllSetting()
    }

    fun initAllSetting() {
        mView.useBikaSource.isChecked = !mViewModel?.getBikaMode()!!
    }
}