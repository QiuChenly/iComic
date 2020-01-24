package com.qiuchenly.comicx.ViewCreator


import android.content.Context
import android.util.AttributeSet

internal class RectCardViewDefault : RectCardView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    internal override fun getViewHeight(widthMeasureSpec: Int, heightMeasureSpec: Int): Int {
        return widthMeasureSpec
    }
}