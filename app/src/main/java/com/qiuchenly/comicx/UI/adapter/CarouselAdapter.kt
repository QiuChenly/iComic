package com.qiuchenly.comicx.UI.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.qiuchenly.comicx.R
import kotlin.random.Random

abstract class CarouselAdapter : PagerAdapter(), Handler.Callback {
    override fun handleMessage(msg: Message?): Boolean {
        if (!aWait) {
            if (msg?.what == mWhat) {
                mHandler?.sendEmptyMessageDelayed(mWhat, 4000)
                if (mCount == mLists?.size) {
                    mCount = 0
                }
                mVP?.currentItem = mCount
                mCount++
            }
        }
        return true
    }

    private var mCount = 0

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    private var mVP: ViewPager? = null
    fun setVP(mPagerInstance: ViewPager?) {
        mVP = mPagerInstance
    }

    private var aWait = true

    /**
     * 暂停播放Banner
     */
    fun aWaitScroll() {
        aWait = true
    }


    private var mWhat = Random(System.currentTimeMillis()).nextInt()
    /**
     * 开始播放Banner
     */
    fun startScroll() {
        mCount = 0
        aWait = false
        mWhat = Random(System.currentTimeMillis()).nextInt()
        mHandler?.sendEmptyMessage(mWhat)
    }


    private var mHandler: Handler? = null

    init {
        mHandler = Handler(Looper.getMainLooper(), this)
    }

    fun setData(Lists: ArrayList<String>) {
        mLists = Lists
    }

    private var mLists: ArrayList<String>? = null

    override fun getCount() = mLists?.size ?: 0

    abstract fun onViewInitialization(mView: View, itemData: String?, position: Int)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mView = LayoutInflater.from(container.context).inflate(R.layout.vpitem_top_ad, null)
        onViewInitialization(mView, mLists?.get(position), position)
        container.addView(mView)
        return mView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}