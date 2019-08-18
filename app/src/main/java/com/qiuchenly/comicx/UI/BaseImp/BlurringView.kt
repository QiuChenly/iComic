package com.qiuchenly.comicx.UI.BaseImp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View
import com.qiuchenly.comicx.R

/**
 * A custom view for presenting getApi dynamically blurred version of another view's content.
 *
 *
 * Use [.setBlurredView] to set up the reference to the view to be blurred.
 * After that, call [.invalidate] to trigger blurring whenever necessary.
 */
class BlurringView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mDownsampleFactor: Int = 0
    private var mOverlayColor: Int = 0

    private var mBlurredView: View? = null
    private var mBlurredViewWidth: Int = 0
    private var mBlurredViewHeight: Int = 0

    private var mDownsampleFactorChanged: Boolean = false
    private var mBitmapToBlur: Bitmap? = null
    private var mBlurredBitmap: Bitmap? = null
    private var mBlurringCanvas: Canvas? = null
    private var mRenderScript: RenderScript? = null
    private var mBlurScript: ScriptIntrinsicBlur? = null
    private var mBlurInput: Allocation? = null
    private var mBlurOutput: Allocation? = null

    init {

        val res = resources
        val defaultBlurRadius = res.getInteger(R.integer.default_blur_radius)
        val defaultDownsampleFactor = res.getInteger(R.integer.default_downsample_factor)
        val defaultOverlayColor = res.getColor(R.color.default_overlay_color)

        initializeRenderScript(context)

        val a = context.obtainStyledAttributes(attrs, R.styleable.PxBlurringView)
        setBlurRadius(a.getInt(R.styleable.PxBlurringView_blurRadius, defaultBlurRadius))
        setDownsampleFactor(
            a.getInt(
                R.styleable.PxBlurringView_downsampleFactor,
                defaultDownsampleFactor
            )
        )
        setOverlayColor(a.getColor(R.styleable.PxBlurringView_overlayColor, defaultOverlayColor))
        a.recycle()
    }

    fun setBlurredView(blurredView: View) {
        mBlurredView = blurredView
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBlurredView != null) {
            if (prepare()) {
                // If the background of the blurred view is getApi color drawable, we use it to clear
                // the blurring canvas, which ensures that edges of the child views are blurred
                // as well; otherwise we clear the blurring canvas with getApi transparent color.
                if (mBlurredView!!.background != null && mBlurredView!!.background is ColorDrawable) {
                    mBitmapToBlur!!.eraseColor((mBlurredView!!.background as ColorDrawable).color)
                } else {
                    mBitmapToBlur!!.eraseColor(Color.TRANSPARENT)
                }

                mBlurredView!!.draw(mBlurringCanvas)
                blur()

                canvas.save()
                canvas.translate(mBlurredView!!.x - x, mBlurredView!!.y - y)
                canvas.scale(mDownsampleFactor.toFloat(), mDownsampleFactor.toFloat())
                canvas.drawBitmap(mBlurredBitmap!!, 0f, 0f, null)
                canvas.restore()
            }
            canvas.drawColor(mOverlayColor)
        }
    }

    fun setBlurRadius(radius: Int) {
        mBlurScript!!.setRadius(radius.toFloat())
    }

    fun setDownsampleFactor(factor: Int) {
        if (factor <= 0) {
            throw IllegalArgumentException("Downsample factor must be greater than 0.")
        }

        if (mDownsampleFactor != factor) {
            mDownsampleFactor = factor
            mDownsampleFactorChanged = true
        }
    }

    fun setOverlayColor(color: Int) {
        mOverlayColor = color
    }

    private fun initializeRenderScript(context: Context) {
        mRenderScript = RenderScript.create(context)
        mBlurScript = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript))
    }

    protected fun prepare(): Boolean {
        val width = mBlurredView!!.width
        val height = mBlurredView!!.height

        if (mBlurringCanvas == null || mDownsampleFactorChanged
            || mBlurredViewWidth != width || mBlurredViewHeight != height
        ) {
            mDownsampleFactorChanged = false

            mBlurredViewWidth = width
            mBlurredViewHeight = height

            var scaledWidth = width / mDownsampleFactor
            var scaledHeight = height / mDownsampleFactor

            // The following manipulation is to avoid some RenderScript artifacts at the edge.
            scaledWidth = scaledWidth - scaledWidth % 4 + 4
            scaledHeight = scaledHeight - scaledHeight % 4 + 4

            if (mBlurredBitmap == null
                || mBlurredBitmap!!.width != scaledWidth
                || mBlurredBitmap!!.height != scaledHeight
            ) {
                mBitmapToBlur = Bitmap.createBitmap(
                    scaledWidth, scaledHeight,
                    Bitmap.Config.ARGB_8888
                )
                if (mBitmapToBlur == null) {
                    return false
                }

                mBlurredBitmap = Bitmap.createBitmap(
                    scaledWidth, scaledHeight,
                    Bitmap.Config.ARGB_8888
                )
                if (mBlurredBitmap == null) {
                    return false
                }
            }

            mBlurringCanvas = Canvas(mBitmapToBlur!!)
            mBlurringCanvas!!.scale(1f / mDownsampleFactor, 1f / mDownsampleFactor)
            mBlurInput = Allocation.createFromBitmap(
                mRenderScript, mBitmapToBlur,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT
            )
            mBlurOutput = Allocation.createTyped(mRenderScript, mBlurInput!!.type)
        }
        return true
    }

    protected fun blur() {
        mBlurInput!!.copyFrom(mBitmapToBlur)
        mBlurScript!!.setInput(mBlurInput)
        mBlurScript!!.forEach(mBlurOutput)
        mBlurOutput!!.copyTo(mBlurredBitmap)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mRenderScript != null) {
            mRenderScript!!.destroy()
        }
    }

}
