package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.viewModel.MainActivityViewModel
import com.qiuchenly.comicx.Utils.CustomUtils
import com.qiuchenly.comicx.databinding.ActivitySwitchMainBinding
import com.qiuchenly.comicx.databinding.NavigationMainBinding
import com.tencent.bugly.beta.Beta

class MainActivity : BaseApp(), View.OnClickListener, BaseApp.ProgressCancel {

    private lateinit var mView: ActivitySwitchMainBinding

    private lateinit var mNavigationMainBinding: NavigationMainBinding

    override fun onCancelRequest() {

    }

    fun getThisViewBing() = mView

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.switch_my_list
                || v.id == R.id.switch_my_website_more
                || v.id == R.id.switch_my_website_addition
                && v.tag != null
            )
                mView.vpMainPages.currentItem = v.tag as Int
        }
    }

    override fun getLayoutID(): View {
//        return R.layout.activity_switch_main
        mView = ActivitySwitchMainBinding.inflate(layoutInflater)
        mNavigationMainBinding = NavigationMainBinding.bind(mView.inDl.root)
        return mView.root
    }

    private lateinit var mViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addProgressCancelListener(this)
        //viewModel处理UI
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mViewModel.setActivity(this)

        CustomUtils.loadImage(
            this@MainActivity,
            "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp",
            mNavigationMainBinding.mWeatherImg,
            5,
            500
        )

        with(mViewModel) {
            message.observe(this@MainActivity, Observer {
                ShowErrorMsg(it)
            })

            mWeatherData.observe(this@MainActivity, Observer {
                mNavigationMainBinding.mDateTemp.text = it.weatherNow.temperature + "°C"
                mNavigationMainBinding.mDateInfo.text =
                    it.weatherDaily[0].weekDay + "/" + it.weatherDaily[0].date
                mNavigationMainBinding.mDateStatus.text =
                    it.weatherNow.text + " " + it.weatherDaily[0].textDay +
                            " " + it.weatherNow.windDirection + "风 " +
                            it.weatherDaily[0].low + "-" + it.weatherDaily[0].high + "°C"
                mNavigationMainBinding.mDatePM.text =
                    it.location.country + " " + it.location.name + " 空气质量:" + it.airNow.quality
                CustomUtils.loadImage(
                    this@MainActivity,
                    it.weatherNow.img,
                    mNavigationMainBinding.mWeatherImgRight,
                    0,
                    200
                )

                mNavigationMainBinding.mUpdateInfo.isRefreshing = false
            })

            randomImage.observe(this@MainActivity, Observer {
                CustomUtils.loadImage(
                    this@MainActivity,
                    it.data[0].src.rawSrc,
                    mNavigationMainBinding.mWeatherImg,
                    0,
                    500
                )
            })
        }

        mNavigationMainBinding.mUpdateInfo.setOnRefreshListener {
            mViewModel.getWeathers()
            Beta.checkUpgrade()//防止有时候没加载出来
        }
        mViewModel.getWeathers()
        mViewModel.getRandomImage()
        Beta.checkUpgrade()//防止有时候没加载出来        //TODO 此处启动后台下载服务暂时不写
        //startService(Intent(this, DownloadService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.message.removeObservers(this)
        mViewModel.cancel()
        removeCancelListener(this)
        System.gc()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (!mViewModel.canExit(keyCode, event)) false
        else super.onKeyUp(keyCode, event)
    }
}