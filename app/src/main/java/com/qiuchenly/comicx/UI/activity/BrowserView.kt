package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.View
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.databinding.ActivityWebviewBinding


class BrowserView : BaseApp() {

    private lateinit var mView: ActivityWebviewBinding

    override fun getLayoutID(): View {
//        R.layout.activity_webview
        mView = ActivityWebviewBinding.inflate(layoutInflater)
        return mView.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mIntent = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        mView.mUrlLoad.settings.javaScriptEnabled = true//JavaScript 启用脚本解决无法加载图片的bug

        if (!mIntent.isNullOrEmpty())
            mView.mUrlLoad.loadUrl(mIntent)
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }
}
