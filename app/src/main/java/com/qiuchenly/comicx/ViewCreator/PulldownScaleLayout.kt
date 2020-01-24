package com.qiuchenly.comicx.ViewCreator

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.qiuchenly.comicx.R
import kotlin.math.abs
import kotlin.math.pow


class PulldownScaleLayout : FrameLayout {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mHeadWidth == 0) {
            mHeadWidth = mHeadView.width
            mHeadHeight = mHeadView.height
        }
    }

    private fun resetImage() {
        println("开始重置🌶")
        val mHeightValueAnimate = ValueAnimator.ofInt(mHeadView.layoutParams.width, mHeadHeight)
        val mWidthValueAnimate = ValueAnimator.ofInt(mHeadView.layoutParams.height, mHeadWidth)
        val mXValueAnimate = ValueAnimator.ofInt(mHeadView.translationX.toInt(), 0)
        mHeightValueAnimate.addUpdateListener {
            mHeadView.layoutParams.height = it.animatedValue as Int
        }
        mWidthValueAnimate.addUpdateListener {
            mHeadView.layoutParams.width = it.animatedValue as Int
        }

        mXValueAnimate.addUpdateListener {
            mHeadView.translationX = it.animatedValue as Int * 1f
            mHeadView.requestLayout()
        }

        val animalSet = AnimatorSet()
        animalSet.duration = 100
        animalSet.play(mHeightValueAnimate).with(mWidthValueAnimate).with(mXValueAnimate)
        animalSet.interpolator = FastOutSlowInInterpolator()
        animalSet.start()
//        mHeadWidth = 0
//        mHeadHeight = 0
    }

    private fun scaleImage(offsetY: Float) {
        println("开始放大🌶")
        val pullOffset = offsetY.pow(0.9f).toInt()
        val newHeight = pullOffset + mHeadHeight
        val newWidth = mHeadWidth//((newHeight.toFloat() / mHeadHeight) * mHeadWidth).toInt()
        val margin = (newWidth - mHeadWidth) / 2
        mHeadView.layoutParams.height = newHeight
        mHeadView.layoutParams.width = newWidth
        mHeadView.translationX = -margin * 1f
        mHeadView.requestLayout()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action ?: return false) {
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_DOWN -> {
                if (isDrag) resetImage()
                true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDrag) scaleImage(event.y - mInitY)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private lateinit var mHeadView: AppCompatImageView
    private var mHeadWidth = 0
    private var mHeadHeight = 0
    override fun onFinishInflate() {
        super.onFinishInflate()
        mHeadView = findViewById(R.id.mRealImageNoBlur)
    }

    private lateinit var onPullDown: () -> Unit
    private lateinit var onResetUI: () -> Unit


    private var mInitX = 0f
    private var mInitY = 0f

    private var isDrag = false
    private var canPull = false

    fun setCanPull(status: Boolean) {
        canPull = status
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action ?: return false) {
            MotionEvent.ACTION_DOWN -> {
                mInitX = ev.x
                mInitY = ev.y
                isDrag = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (!canPull) return super.onInterceptTouchEvent(ev)
                val differentX = ev.x - mInitX
                val differentY = ev.y - mInitY
                if (differentY > 0 && differentY / abs(differentX) > 2) {
                    isDrag = true
                    return true
                }
            }
            else -> return super.onInterceptTouchEvent(ev)
        }
        return super.onInterceptTouchEvent(ev)
    }
}