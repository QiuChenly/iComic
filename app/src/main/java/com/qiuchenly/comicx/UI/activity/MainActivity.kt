package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.viewModel.MainActivityViewModel
import com.qiuchenly.comicx.Utils.CustomUtils
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_switch_main.*
import kotlinx.android.synthetic.main.navigation_main.*


class MainActivity : BaseApp(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.switch_my_list
                || v.id == R.id.switch_my_website_more
                || v.id == R.id.switch_my_website_addition
                && v.tag != null
            )
                vp_main_pages.currentItem = v.tag as Int
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_switch_main
    }

    private lateinit var mViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel处理UI
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mViewModel.setActivity(this)

        CustomUtils.loadImage(
            this@MainActivity,
            "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp",
            mWeatherImg,
            5,
            500
        )

        with(mViewModel) {
            mWeatherData.observe(this@MainActivity, Observer {
                mDateTemp.text = it.weatherNow.temperature + "°C"
                mDateInfo.text = it.weatherDaily[0].weekDay + "/" + it.weatherDaily[0].date
                mDateStatus.text =
                    it.weatherNow.text + " " + it.weatherDaily[0].textDay +
                            " " + it.weatherNow.windDirection + "风 " +
                            it.weatherDaily[0].low + "-" + it.weatherDaily[0].high + "°C"
                mDatePM.text = "空气质量:" + it.airNow.quality
                CustomUtils.loadImage(
                    this@MainActivity,
                    it.weatherNow.img,
                    mWeather_img,
                    0,
                    200
                )

                mUpdateInfo.isRefreshing = false
            })

            randomImage.observe(this@MainActivity, Observer {
                CustomUtils.loadImage(
                    this@MainActivity,
                    it.data[0].src.rawSrc,
                    mWeatherImg,
                    0,
                    500
                )
            })
        }

        mUpdateInfo.setOnRefreshListener {
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
        mViewModel.cancel()
        System.gc()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (!mViewModel.canExit(keyCode, event)) false
        else super.onKeyUp(keyCode, event)
    }
}