package com.qiuchenly.comicx.UI.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.Utils.DisplayUtil
import kotlinx.android.synthetic.main.item_comicpage.view.*
import java.lang.ref.WeakReference


class ComicReadingAdapter(loadListener: LoaderListener, private val mContext: WeakReference<Context>) :
    BaseRecyclerAdapter<String>() {

    init {
        setLoadMoreCallBack(loadListener)
    }

    private var TAG = "ComicReadingAdapter"

    override fun canLoadMore() = true

    override fun getItemLayout(viewType: Int) = R.layout.item_comicpage

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        if (mContext.get() == null)
            return
        with(item) {
            mRetryLoad.setOnClickListener {
                mRetryLoad.text = "加载中..."
                mRetryLoad.isClickable = false
                mRetryLoad.visibility = View.INVISIBLE
                onViewShow(item, data, position, ViewType)
            }
            tv_imageIndex.visibility = View.VISIBLE
            tv_imageIndex.text = "图${position + 1}...加载中"
            //解决图片莫名加载模糊的bug
            //1.使用override(1080,Integer.MAX_VALUE)复写图片预加载大小
            //2.使用asBitmap将Drawable变成位图
            //3.val lp = iv_img_page.layoutParams
            //  lp.width = DisplayUtil.getScreenWidth(Comic.getContext())
            //  lp.height = DisplayUtil.getScreenWidth(Comic.getContext()) * resource.height / resource.width
            //重新计算占位大小
            Glide.with(mContext.get()!!)
                .asBitmap()
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(1080, Int.MAX_VALUE)
                .transition(BitmapTransitionOptions.withCrossFade(200))
                //.format(DecodeFormat.PREFER_ARGB_8888)
                .addListener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        mRetryLoad.visibility = View.VISIBLE
                        mRetryLoad.isClickable = true
                        mRetryLoad.text = "点击重试!"
                        tv_imageIndex.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (resource == null) {
                            mRetryLoad.visibility = View.VISIBLE
                            mRetryLoad.isClickable = true
                            mRetryLoad.text = "点击重试!"
                            return false
                        }
                        tv_imageIndex.visibility = View.INVISIBLE
                        mRetryLoad.visibility = View.INVISIBLE
                        val lp = iv_img_page.layoutParams
                        lp.width = DisplayUtil.getScreenWidth(Comic.getContext())
                        lp.height = DisplayUtil.getScreenWidth(Comic.getContext()) * resource.height / resource.width
                        iv_img_page.layoutParams = lp
                        return false
                    }
                })
                .into(iv_img_page)
            if (position + 1 < getRealSize()) {
                //return@with//这里preload有闪退问题,先屏蔽了再说
                Log.d(TAG, "onViewShow: Size = " + getRealSize() + ", position = " + (position + 1))
                Glide.with(mContext.get()!!)
                    .asBitmap()
                    .load(data)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .override(1080, Int.MAX_VALUE)
                    //.format(DecodeFormat.PREFER_ARGB_8888)
                    .preload()
            }
        }
    }
}