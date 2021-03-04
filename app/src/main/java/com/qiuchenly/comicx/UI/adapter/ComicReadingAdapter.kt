package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.Utils.CustomUtils
import com.qiuchenly.comicx.Utils.DisplayUtil
import com.qiuchenly.comicx.databinding.ItemComicpageBinding
import com.qiuchenly.comicx.databinding.ItemNextPageLoadBinding
import java.lang.ref.WeakReference


class ComicReadingAdapter(
    loadListener: LoaderListener,
    private val mContext: WeakReference<Context>
) :
    BaseRecyclerAdapter<String>() {

    init {
        setLoadMoreCallBack(loadListener)
    }

    private var TAG = "ComicReadingAdapter"

    override fun canLoadMore() = true

    private val TYPE_NEXT_PAGE = 0x01

    override fun getItemLayout(viewType: Int) = when (viewType) {
        TYPE_NEXT_PAGE -> R.layout.item_next_page_load
        else -> R.layout.item_comicpage
    }

    override fun getViewType(position: Int): Int {
        return when {//woc 还能这么用???
            isInternalType(position) -> super.getViewType(position)
            getIndexData(position).indexOf("nextPages") != -1 -> TYPE_NEXT_PAGE
            else -> super.getViewType(position) //必须写个返回值,其实永远不会执行这里
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        if (mContext.get() == null)
            return
        if (ViewType == TYPE_NEXT_PAGE) {
            //我就随便这么写写,觉得不行的可以爬
            val pageLoad = ItemNextPageLoadBinding.bind(item)
            pageLoad.tvNextPage.text = "下面的章节是:" + CustomUtils.subStr(data, "[", "]")
            return
        }
        with(item) {
            val mItemComicPage = ItemComicpageBinding.bind(this)
            mItemComicPage.mRetryLoad.setOnClickListener {
                mItemComicPage.mRetryLoad.text = "加载中..."
                mItemComicPage.mRetryLoad.isClickable = false
                mItemComicPage.mRetryLoad.visibility = View.INVISIBLE
                onViewShow(item, data, position, ViewType)
            }
            mItemComicPage.tvImageIndex.visibility = View.VISIBLE
            mItemComicPage.tvImageIndex.text = "图${position + 1}...加载中"
            //解决图片莫名加载模糊的bug
            //1.使用override(1080,Integer.MAX_VALUE)复写图片预加载大小
            //2.使用asBitmap将Drawable变成位图
            //3.val lp = iv_img_page.layoutParams
            //  lp.width = DisplayUtil.getScreenWidth(Comic.getContext())
            //  lp.height = DisplayUtil.getScreenWidth(Comic.getContext()) * resource.height / resource.width
            //重新计算占位大小
            Glide.with(mContext.get()!!)
                //.asBitmap()
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(1080, Int.MAX_VALUE)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                //.format(DecodeFormat.PREFER_ARGB_8888)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        mItemComicPage.mRetryLoad.visibility = View.VISIBLE
                        mItemComicPage.mRetryLoad.isClickable = true
                        mItemComicPage.mRetryLoad.text = "点击重试!"
                        mItemComicPage.tvImageIndex.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (resource == null) {
                            mItemComicPage.mRetryLoad.visibility = View.VISIBLE
                            mItemComicPage.mRetryLoad.isClickable = true
                            mItemComicPage.mRetryLoad.text = "点击重试!"
                            return false
                        }
                        mItemComicPage.tvImageIndex.visibility = View.INVISIBLE
                        mItemComicPage.mRetryLoad.visibility = View.INVISIBLE
                        val lp = mItemComicPage.ivImgPage.layoutParams
                        lp.width = DisplayUtil.getScreenWidth(Comic.getContext())
                        lp.height =
                            DisplayUtil.getScreenWidth(Comic.getContext()) * resource.intrinsicHeight / resource.intrinsicWidth
                        mItemComicPage.ivImgPage.layoutParams = lp
                        return false
                    }
                })
                .into(mItemComicPage.ivImgPage)
            if (position + 1 < getRealSize()) {
                return@with//这里preload有闪退问题,先屏蔽了再说
                Log.d(TAG, "onViewShow: Size = " + getRealSize() + ", position = " + (position + 1))
                Glide.with(mContext.get()!!)
                    //.asBitmap()
                    .load(data)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .override(1080, Int.MAX_VALUE)
                    //.format(DecodeFormat.PREFER_ARGB_8888)
                    .preload()
            }
        }
    }
}