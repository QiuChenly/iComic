package com.qiuchenly.comicx.ViewCreator

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.R
import kotlinx.android.synthetic.main.fragment_my_details.view.*


class RefreshView(context: Context, attributes: AttributeSet) : FrameLayout(context, attributes) {

    val mScroller = Scroller(context)
    lateinit var mHeadView: View
    lateinit var mContentView: View
    lateinit var mFootView: View

    var mHeadWidth: Int = 0
    var mHeadHeight: Int = 0
    var mContentWidth: Int = 0
    var mContentHeight: Int = 0
    var mFootWidth: Int = 0
    var mFootHeight: Int = 0

    var isIntercept = false

    lateinit var mRefreshProgress: ProgressBar
    lateinit var tvRefreshText: TextView
    lateinit var mRecyclerView: RecyclerView
    lateinit var ivRefreshIcon: ImageView
    override fun onFinishInflate() {
        super.onFinishInflate()
        mHeadView = getChildAt(0)
        mContentView = getChildAt(1)
        mFootView = getChildAt(2)

        mRefreshProgress = mHeadView.findViewById(R.id.refresh_progress)
        tvRefreshText = mHeadView.findViewById(R.id.tv_refresh_state)

        mRecyclerView = mContentView as RecyclerView

        ivRefreshIcon = mHeadView.findViewById(R.id.iv_refreshing)
    }

    fun setBackgroundImage(img: Int) {
        if (img > 0) {
            with(mHeadView) {
                //                mBackground.background = resources.getDrawable(img)
                //mBackground.setImageResource(img)
                mBackground.setImageDrawable(
                    ContextCompat.getDrawable(
                        Comic.getContext() ?: return,
                        img
                    )
                )
                mHeadView.layoutParams =
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                requestLayout()
                //invalidate()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeadHeight = mHeadView.measuredHeight
        mHeadWidth = mHeadView.measuredWidth
        mFootHeight = mFootView.measuredHeight
        mFootWidth = mFootView.measuredWidth

        mContentHeight = mContentView.measuredHeight
        mContentWidth = mContentView.measuredWidth
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mHeadView.layout(0, -mHeadHeight, mHeadWidth, 0)
        mRealHeight = mHeadHeight
        mRefreshHeight = mHeadHeight / 3
        mContentView.layout(0, 0, mContentWidth, mContentHeight)
        mFootView.layout(0, mContentHeight, mFootWidth, mContentHeight + mFootHeight)
    }

    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    var startx = 0f
    var starty = 0f
    var isTop = false
    var isBottom = false
    var firstDownTag = 0
    var mRefreshHeight = 0
    var mRealHeight = mRefreshHeight
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        val x = event.x
        val y = event.y
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startx = x
                starty = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTop) {
                    if (firstDownTag == 0) {
                        /**
                         * 如果是第一次的话，因为事件传递原因
                         * onInterceptTouchEvent()执行了 ACTION_DOWN事件
                         * 标记了startY的值（这个值也许非常大，是根据手指按下的y坐标来定的）
                         * 关键是onTouchEvent的ACTION_DOWN无法得到执行，所以 scrollTo(0, disY);将直接移动到startY的位置
                         * 效果就是导致第一次向下拉，瞬间移动了非常多
                         */
                        firstDownTag++
                    } else {
                        val dy = y - starty
                        var disY = (scrollY - dy).toInt()
                        if (-disY <= 0) {
                            disY = 0
                        }

                        if (-disY < mHeadHeight) {
                            scrollTo(0, disY)
                            mRefreshProgress.visibility = INVISIBLE
                            if (-disY < mRefreshHeight) {
                                tvRefreshText.text = "下拉刷新"
                                startRefreshIcon()
                            } else {
                                tvRefreshText.text = "松开刷新"
                                stopRefreshIcon()
                            }
                        }
                    }
                }
                startx = x
                starty = y
            }
            MotionEvent.ACTION_UP -> {
                isIntercept = false
                if (-scrollY > mRefreshHeight) {
                    startRefreshing()
                } else {
                    stopRefreshing(true)
                }
            }

        }

        return true
    }

    var upX = 0f
    var upY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev ?: return false
        val x = ev.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                isIntercept = false
                upX = x
                upY = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (onUpdate) return false
                isTop = mRecyclerView.computeVerticalScrollOffset() < 1
                isBottom = isSlideToBottom(mRecyclerView)
                if (isTop) {
                    if (upY - y < 0) {
                        isIntercept = true
                    } else if (y - upY < 0) {
                        isIntercept = false
                    }
                } else if (isBottom) {
                    /* 拦截上拉操作 */

                }
            }
            MotionEvent.ACTION_UP -> {
                upY = 0f
                upX = 0f
            }
        }
        return isIntercept
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    interface callback {
        fun onRefresh()
        fun onLoadMore()
    }

    private var mCallback: callback? = null
    fun setUpdate(update: callback) {
        mCallback = update
    }

    fun setTintColor(cls:Int){
        tvRefreshText.setTextColor(cls)
    }

    fun stopRefreshing(isUserCall: Boolean = false) {
        if (!isUserCall)
            tvRefreshText.text = "刷新完毕~"
        val len = if (isUserCall) 0L else 1000L
        handler.postDelayed({
            ivRefreshIcon.clearAnimation()
            mScroller.startScroll(scrollX, scrollY, 0, -scrollY)
            /**
             * ListView子项移动到第一个
             */
            mRecyclerView.scrollToPosition(0)
            invalidate()
            onUpdate = !true
        }, len)
    }

    private var onUpdate = false
    private fun startRefreshing() {
        onUpdate = true
        mScroller.startScroll(scrollX, scrollY, 0, -mRealHeight - scrollY)
        tvRefreshText.text = "刷新中~"
        mRefreshProgress.visibility = View.VISIBLE
        startIconAnimation()
        invalidate()
        mCallback?.onRefresh()
        //handler.postDelayed({ stopRefreshing() }, 2000)
    }

    /*  private fun startLoading() {
          mScroller.startScroll(scrollX, scrollY, 0, mFooterHeight - scrollY)
          ivLoadingIcon.setVisibility(View.INVISIBLE)
          mLoadingProgress.setVisibility(View.VISIBLE)
          invalidate()
          handler.postDelayed({ stopLoading() }, 1500)
      }

      private fun stopLoading() {
          mScroller.startScroll(scrollX, scrollY, 0, -scrollY, 1500)
          ivLoadingIcon.setVisibility(View.VISIBLE)
          mLoadingProgress.setVisibility(View.INVISIBLE)
          ivLoadingIcon.setPivotX(ivLoadingIcon.getWidth() / 2)
          ivLoadingIcon.setPivotY(ivLoadingIcon.getHeight() / 2)
          ivLoadingIcon.setRotation(180)
          invalidate()
      }
  */
    private fun startIconAnimation() {
        val animation = TranslateAnimation(
            0f, 0f,
            scaleY, (-mRealHeight).toFloat()
        )
        animation.fillAfter = false
        animation.duration = 800
        ivRefreshIcon.startAnimation(animation)
    }

    private fun startRefreshIcon() {
        ivRefreshIcon.pivotX = ivRefreshIcon.width / 2f
        ivRefreshIcon.pivotY = ivRefreshIcon.height / 2f
        ivRefreshIcon.rotation = 180f
    }

    private fun stopRefreshIcon() {
        ivRefreshIcon.pivotX = ivRefreshIcon.width / 2f
        ivRefreshIcon.pivotY = ivRefreshIcon.height / 2f
        ivRefreshIcon.rotation = 360f
    }
}