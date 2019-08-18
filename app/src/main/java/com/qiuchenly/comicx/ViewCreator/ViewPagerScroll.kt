package com.qiuchenly.comicx.ViewCreator

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class ViewPagerScroll(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    fun aWaitScroll() {

    }


    fun startScroll() {

    }

    fun setData(Lists: ArrayList<String>) {
        mLists = Lists
    }

    private var mLists: ArrayList<String>? = null


}