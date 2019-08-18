package com.qiuchenly.comicx.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicx.Bean.ApplicationSetting
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Common.BaseURL
import com.qiuchenly.comicx.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object CustomUtils {

    enum class GlideState {
        LoadFailed, ResourceReady
    }

    interface ImageListener : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return onRet(CustomUtils.GlideState.LoadFailed, null, target)
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return onRet(CustomUtils.GlideState.ResourceReady, resource, target)
        }

        fun onRet(
            state: GlideState,
            resource: Drawable?,
            target: Target<Drawable>?
        ): Boolean
    }
/*
    fun blurs(bitmap: Bitmap, radius: Int): Bitmap {
        return StackBlur.blurNatively(bitmap, radius, false)
    }
    */

    fun catchBitmap(view: View, bitmap: Bitmap): Bitmap {
        val bit1 = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bit1)
        canvas.translate(-view.left.toFloat(), -view.top.toFloat())
        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), null)
        return bit1
    }

    fun loadImage(ctx: Context, imageSrc: String?, mView: ImageView) {
        loadImage(ctx, imageSrc!!, mView, -1)
    }

    fun loadImage(ctx: Context, imageSrc: String, mView: ImageView, BlurRadius: Int) {
        loadImage(ctx, imageSrc, mView, BlurRadius, 0, null, 0)
    }

    fun loadImage(ctx: Context, imageSrc: String, mView: ImageView, BlurRadius: Int, crossFade: Int) {
        loadImage(ctx, imageSrc, mView, BlurRadius, 0, null, crossFade)
    }

    fun loadImage(
        ctx: Context,
        imageSrc: String,
        mView: ImageView,
        loadingImg: Int,
        lister: ImageListener?,
        crossFade: Int
    ) {
        loadImage(ctx, imageSrc, mView, -1, loadingImg, lister, crossFade)
    }

    fun loadImageEx(ctx: Context, imageSrc: String, mView: ImageView, loadingImg: Int, lister: ImageListener?) {
        val builder = Glide.with(ctx)
            .load(imageSrc)
            .apply {
                if (loadingImg > 0)
                    placeholder(loadingImg)
                if (lister != null)
                    listener(lister)
                transition(DrawableTransitionOptions.withCrossFade(400))
            }
        builder.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mView)
    }

    fun loadImageCircle(ctx: Context, imageSrc: String, mView: ImageView) {
        val builder = Glide.with(ctx)
            .load(imageSrc)
            .apply {
                //圆角：RequestOptions.bitmapTransform(new RoundedCorners( 5))
                //圆形：RequestOptions.bitmapTransform(new CircleCrop())
                apply(RequestOptions.bitmapTransform(CircleCrop()))
                transition(DrawableTransitionOptions.withCrossFade(400))
            }
        builder.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mView)
    }

    fun loadImageCircle(ctx: Context, imageSrc: String, mView: ImageView, mCorners: Int = 0) {
        val builder = Glide.with(ctx)
            .load(imageSrc)
            .apply {
                //圆角：RequestOptions.bitmapTransform(new RoundedCorners( 5))
                //圆形：RequestOptions.bitmapTransform(new CircleCrop())
                if (mCorners > 0)
                    apply(RequestOptions.bitmapTransform(RoundedCorners(mCorners)))
                else
                    apply(RequestOptions.bitmapTransform(CircleCrop()))
                transition(DrawableTransitionOptions.withCrossFade(400))
            }
        builder.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mView)
    }

    fun loadImage(
        ctx: Context,
        imageSrc: String,
        mView: ImageView,
        BlurRadius: Int,
        loadingImg: Int,
        lister: ImageListener?,
        crossFade: Int,
        Simple: Int = 10
    ) {
        val builder = Glide.with(ctx)
            .load(imageSrc)
            .apply {
                if (BlurRadius > 0)
                    apply(bitmapTransform(BlurTransformation(BlurRadius, Simple)))
                if (loadingImg > 0)
                    placeholder(loadingImg)
                if (lister != null)
                    listener(lister)
                //if (crossFade > 0)
                transition(DrawableTransitionOptions.withCrossFade(400))
            }
        builder.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mView)
    }


    /**
     * 快速排序算法
     * @param MODE =1 正序 =2 倒序
     */
    fun <T> sort(MODE: Int, map: ArrayList<T>): ArrayList<T> {
        val ret = ArrayList<T>()
        for (m: T in map) {
            ret.add(if (MODE == 1) 0 else ret.size, m)
        }
        return ret
    }

    /**
     * 快速排序算法
     */
    fun <T> sortEx(map: ArrayList<T>): ArrayList<T> {
        map.reverse()
        return map
    }

    private var notify: NotificationManager? = null
    private var channel: NotificationChannel? = null
    private val channelID = "1"
    private val channelName = "channel_name"
    @RequiresApi(Build.VERSION_CODES.O)
    fun SendNotificationPlus(ctx: Context, title: String, Content: String, typeID: Int) {
        if (channel == null) {
            channel = NotificationChannel(
                channelID,
                channelName,
                NotificationManager.IMPORTANCE_MIN
            )
        }
        if (notify == null) {
            notify = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        }
        notify!!.createNotificationChannel(channel!!)
        val builder = Notification.Builder(ctx)
            .setContentText(Content)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setChannelId(channelID)
        val notification = builder.build()
        notify!!.notify(typeID, notification)
    }

    fun SendNotification(ctx: Context, title: String, Content: String, typeID: Int) {
        if (notify == null) {
            notify = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        }
        val notification = NotificationCompat.Builder(ctx)
            .setContentTitle(title)
            .setContentText(Content)
            .setSmallIcon(R.mipmap.ic_launcher)
            //单个设置
            .setVibrate(null)
            .setVibrate(longArrayOf(0L))
            .setSound(null)
            .setLights(0, 0, 0)
            .build()
        notify!!.notify(typeID, notification)
    }

    fun SendNotificationEx(ctx: Context, title: String, Content: String, typeID: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SendNotificationPlus(ctx, title, Content, typeID)
        } else {
            SendNotification(ctx, title, Content, typeID)
        }
    }


    fun MD5(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            val sb = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                val i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0$hexString"
                }
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getBytesByBitmap(bitmap: Bitmap): ByteArray {
        val buffer = ByteBuffer.allocate(bitmap.byteCount)
        return buffer.array()
    }

    /**
     * 取文本中间
     *
     * @param all   欲取文本中间的全文本
     * @param left  欲取文本的左边文本
     * @param right 欲取文本的右边文本
     * @return 返回得到的文本
     */
    fun subStr(all: String, left: String, right: String): String {
        val sta = all.indexOf(left) + left.length
        return all.substring(sta, all.indexOf(right, sta))
    }

    fun setCachedBingUrl(url: String) {
        val instance = Comic.getRealm()
        var single = instance?.where(ApplicationSetting::class.java)?.findFirst()
        instance?.beginTransaction()
        if (single == null) {
            single = ApplicationSetting().apply {
                mBingCachedUrl = url
            }
            instance?.copyToRealm(single)
        } else {
            single.mBingCachedUrl = url
        }
        instance?.commitTransaction()
    }

    fun getCachedBingUrl(): String {
        val single = Comic.getRealm()?.where(ApplicationSetting::class.java)?.findFirst()
        return if (single?.mBingCachedUrl != null) single.mBingCachedUrl!! else BaseURL.BASE_IMAGE_DEFAULT
    }

}
