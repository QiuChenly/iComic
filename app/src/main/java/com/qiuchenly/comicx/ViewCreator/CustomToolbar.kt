package com.qiuchenly.comicx.ViewCreator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.qiuchenly.comicx.R
import kotlinx.android.synthetic.main.classic_toolbar.view.*

class CustomToolbar : FrameLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mStatusBarHeight = context.resources.getDimensionPixelSize(
            context.resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        )

        LayoutInflater.from(context).inflate(R.layout.classic_toolbar, this)
        classic_toolbar.layoutParams =
            LayoutParams(classic_toolbar.layoutParams).apply {
                setMargins(0, mStatusBarHeight, 0, 0)
            }

    }

    var mStatusBarHeight = 0


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        toolbar_height = mToolbar.measuredHeight
        toolbar_width = mToolbar.measuredWidth
    }


    var toolbar_height = 0
    var toolbar_width = 0
    lateinit var mToolbar: View
    override fun onFinishInflate() {
        super.onFinishInflate()
        mToolbar = getChildAt(0)

    }
}