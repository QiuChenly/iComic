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
import com.qiuchenly.comicx.databinding.ActivityRecentlyReadBinding
import com.qiuchenly.comicx.databinding.DialogConfirmClearAllRecentlyBinding
import com.r0adkll.slidr.Slidr
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

    private lateinit var mRecentlyReadBinding: ActivityRecentlyReadBinding
    private lateinit var mRecentlyReadDialog: DialogConfirmClearAllRecentlyBinding
    fun InitUI(arr: ArrayList<SuperPagerAdapter.Struct>) {

        mView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_confirm_clear_all_recently, null, false)
        mDialog = WeakReference(
            AlertDialog.Builder(this)
                .setView(mView)
                .setCancelable(false)
                .create()
        )

        mRecentlyReadBinding = ActivityRecentlyReadBinding.inflate(layoutInflater)
        mRecentlyReadDialog = DialogConfirmClearAllRecentlyBinding.bind(mView!!.rootView)


        //init ui
        mRecentlyReadBinding.backUp.setOnClickListener {
            finish()
        }
        mRecentlyReadBinding.clearAll.setOnClickListener {
            with(mView) {
                mRecentlyReadDialog.tvDialogTitle.text = "重要操作"
                mRecentlyReadDialog.tvDialogContent.text = "这样将会导致整个最近阅读与漫画阅读进度丢失!\n\n真的要这么做吗？\n" +
                        "\n(你以为你还有选择吗?)"

                mRecentlyReadDialog.tvDialogContent.setOnClickListener {
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
                mRecentlyReadDialog.btnDialogConfirm.setOnClickListener(callback)
                mRecentlyReadDialog.btnDialogCancel.setOnClickListener(callback)
            }
            mDialog?.get()?.show()
        }

        mPgAdapter = SuperPagerAdapter(supportFragmentManager, arr)
        mRecentlyReadBinding.tlRecentlyTabSetupVp.adapter = mPgAdapter

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(
            this.applicationContext,
            arr,
            mRecentlyReadBinding.miMagicIndicator.magicIndicator,
            mRecentlyReadBinding.tlRecentlyTabSetupVp
        )
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