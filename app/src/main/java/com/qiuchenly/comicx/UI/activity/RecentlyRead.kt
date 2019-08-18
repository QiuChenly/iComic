package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicx.UI.BaseImp.SuperPagerAdapter
import com.qiuchenly.comicx.UI.fragment.RecentlyByWeekFragment
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_recently_read.*
import kotlinx.android.synthetic.main.dialog_confirm_clear_all_recently.view.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*
import java.lang.ref.WeakReference

class RecentlyRead : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_read)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        Slidr.attach(this)
        if (supportActionBar != null) supportActionBar!!.hide()
        InitUI(getFramList())
    }

    fun getFramList(): ArrayList<SuperPagerAdapter.Struct> {
        return arrayListOf(
            SuperPagerAdapter.Struct("最近阅读", RecentlyByWeekFragment())
            //,SuperPagerAdapter.Struct("一月之前", Fragment())
        )
    }

    private var mDialog: WeakReference<AlertDialog>? = null
    private var mView: View? = null
    private var mPgAdapter: SuperPagerAdapter? = null
    fun InitUI(arr: ArrayList<SuperPagerAdapter.Struct>) {

        mView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_confirm_clear_all_recently, null, false)
        mDialog = WeakReference(
            AlertDialog.Builder(this)
                .setView(mView)
                .setCancelable(false)
                .create()
        )

        //init ui
        back_up.setOnClickListener {
            finish()
        }
        clear_all.setOnClickListener {
            with(mView) {
                this?.tv_dialog_title?.text = "重要操作"
                this?.tv_dialog_content?.text = "这样将会导致整个最近阅读与漫画阅读进度丢失!\n\n真的要这么做吗？\n" +
                        "\n(你以为你还有选择吗?)"

                this?.tv_dialog_content?.setOnClickListener {
                    mDialog?.get()?.dismiss()
                }

                val callback = View.OnClickListener {
                    Comic.getRealm()?.executeTransaction { mRealm ->
                        mRealm.where(RecentlyReadingBean::class.java)
                            .findAll()
                            .deleteAllFromRealm()
                    }
                    mDialog?.get()?.dismiss()
                    call()
                }
                this?.btn_dialog_confirm?.setOnClickListener(callback)
                this?.btn_dialog_cancel?.setOnClickListener(callback)
            }
            mDialog?.get()?.show()
        }
        val list = arr
        mPgAdapter = SuperPagerAdapter(supportFragmentManager, list)
        tl_recently_tab_setup_vp.adapter = mPgAdapter

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this.applicationContext, list, magic_indicator, tl_recently_tab_setup_vp)
    }

    fun call() {
        (mPgAdapter?.getInstance("最近阅读") as RecentlyByWeekFragment).reInitData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDialog = null
        mView = null
    }
}