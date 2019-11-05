package com.qiuchenly.comicx.UI.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.qiuchenly.comicx.Bean.WelcomeLang
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Common.NMSL.WelcomeLangClient
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.tencent.bugly.Bugly
import kotlinx.android.synthetic.main.splash_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SplashActivity : BaseApp() {

    private var TAG = "SplashActivity"
    override fun getLayoutID() = com.qiuchenly.comicx.R.layout.splash_view

    override fun getUISet(mSet: UISet) = mSet.apply {
        this.isFullScreen = true
        this.isSlidr = false
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //isZuiChou = PreferenceHelper.getZuiChou(Comic.getContext())

        val temp =
            "appkey=4409e2ce8ffd12b8&build=101700&mobi_app=android_tv_yst&page_id=20&platform=android&ts=1555949735"

        val map = HashMap<String, String>()

        for (str in temp.split("&")) {
            val single = str.split("=")
            map[single[0]] = single[1]
        }
        //appkey=4409e2ce8ffd12b8&auth_code=07ac650994f71282265747c8daa27231&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android
        /*map.put("appkey", "4409e2ce8ffd12b8");
        map.put("auth_code", "07ac650994f71282265747c8daa27231");
        map.put("build", "101602");
        map.put("channel", "master");
        map.put("guid", "XZE72D6E5AAB98D33703546128A2DA8335867");
        map.put("mobi_app", "android_tv_yst");
        map.put("platform", "android");*/
        //appkey=4409e2ce8ffd12b8&auth_code=ff38da97755466f53d4add35420a934b&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android&ts=1554050734
        //val ss = LibBili.a(map)
        //System.out.println(ss.toString())
        //return;
        mLang.text = "三天之内,祝你心想事成."
        mLangAuthor.text = "--- 三日杀神"

        WelcomeLangClient.generateNiceLang()
        WelcomeLangClient.getAPI()?.getNiceOne()?.enqueue(object : Callback<WelcomeLang> {
            override fun onResponse(call: Call<WelcomeLang>, response: Response<WelcomeLang>) {
                mLang.text = "  " + response.body()?.hitokoto
                mLangAuthor.text = "--- " + response.body()?.from
                final()
            }

            override fun onFailure(call: Call<WelcomeLang>, t: Throwable) {
                ShowErrorMsg(t.message ?: "加载失败")
                final()
            }
        })

        //bugly 崩溃测试
        //CrashReport.testJavaCrash()
        val mVersionName: String
        val vars =
            Comic.getContext()?.packageManager?.getPackageInfo(Comic.getContext()?.packageName, 0)
        mVersionName = vars?.versionName ?: "获取App版本失败"

        mAuthorName.text = "QiuChenly Design $mVersionName"
    }

    fun final() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Bugly.init(applicationContext, "f4b1fcb8dd", false)
        }, 5000)
    }
}