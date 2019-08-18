package com.qiuchenly.comicx.Core

import android.annotation.SuppressLint
import android.content.Context
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("StaticFieldLeak")
object Comic {
    private var mContext: Context? = null
    /**
     * 64 bits
     * @return
     */
    private fun getKey(): ByteArray {
        return byteArrayOf(
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1,
            0,
            1,
            2,
            3,
            4,
            3,
            2,
            1
        )
    }

    fun initialization(ctx: Context) {
        mContext = ctx
        Realm.init(mContext!!)
        val config = RealmConfiguration.Builder()
            .name("realm.my_hooks")// 库文件名
            .encryptionKey(getKey())  // 加密
            .schemaVersion(1)  // 版本号
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()

        //记录崩溃信息
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            //获取崩溃时的UNIX时间戳
            val timeMillis = System.currentTimeMillis()
            //将时间戳转换成人类能看懂的格式，建立一个String拼接器
            val stringBuilder = StringBuilder(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date(timeMillis)))
            stringBuilder.append(":\n")
            //获取错误信息
            stringBuilder.append(throwable.message)
            stringBuilder.append("\n")
            //获取堆栈信息
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            throwable.printStackTrace(pw)
            stringBuilder.append(sw.toString())

            //这就是完整的错误信息了，你可以拿来上传服务器，或者做成本地文件保存等等等等
            val errorLog = stringBuilder.toString()
            //最后如何处理这个崩溃，这里使用默认的处理方式让APP停止运行
            defaultHandler.uncaughtException(thread, throwable)
        }

        val isFirst = PreferenceHelper.getIsFirst(ctx)
        if (isFirst) PreferenceHelper.setNoLoginBika(ctx, false)
    }

    private var realm: Realm? = null
    fun getRealm() = realm

    fun getContext() = mContext

    fun closed() {
        getRealm()?.close()
        realm = null
        mContext = null
    }
}