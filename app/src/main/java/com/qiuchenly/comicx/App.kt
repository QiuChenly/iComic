package com.qiuchenly.comicx

import android.app.Application
import androidx.multidex.MultiDex
import com.orhanobut.hawk.Hawk
import com.qiuchenly.comicx.Core.Comic
import kotlin.system.exitProcess

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this)
            .build()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
        //解决dex方法超过65535
        MultiDex.install(this)
        //检查内存泄漏
//        LeakCanary.install(this)
        Comic.initialization(this)
    }

    companion object {
        private val TAG = "App"

        fun closedApp() {
            Comic.closed()
            exitProcess(0)
        }
    }
}
