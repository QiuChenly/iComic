package com.qiuchenly.comicx.Utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * 下载任务类,整合下载任务
 */
class DownloadHelper(Home: String) : Thread() {
    companion object DownUtils {
        @SuppressLint("StaticFieldLeak")
        private var mDownHelp: DownloadHelper? = null
        private var mApplicationContext: Context? = null
        fun getInstance(mApplicationContext: Context, Home: String): DownloadHelper {
            this.mApplicationContext = mApplicationContext
            if (mDownHelp == null) mDownHelp = DownloadHelper(Home)
            return mDownHelp!!
        }

        fun getInstance(): DownloadHelper? {
            return if (mApplicationContext == null || mDownHelp == null) null else mDownHelp
        }
    }

    private var mHomeFileName = Home
    private var mTaskList = ArrayList<DownInfo>()

    /**
     * 添加一个下载任务
     * @param url 下载的实际URL
     * @param currentFile 存储在本地的实际文件名称
     */
    fun mStart(
        url: String,
        currentFile: String,
        downloadCallback: DownloadCallback
    ): Int {
        val mDownInfo = DownInfo().apply {
            this.mDownFileName = currentFile
            this.mDownUrl = url
            this.mDownTaskID = GenerateTaskTag()
        }

        return 0
    }

    fun GenerateTaskTag(): Int {
        return System.currentTimeMillis().toString().substring(7, 13).toInt()
    }

    fun mPauseDownload(mTaskID: Int) {

    }

    fun mResumeDownload(mTaskID: Int) {

    }

    fun mCancelDownload(mTaskID: Int) {

    }

    fun mGetTaskInfo(mTaskID: Int) {

    }


    interface DownloadCallback {
        fun onDownloadStart()
        fun onDownloadOver()
        fun onDownloadCancel()
    }

    open class DownInfo {
        var mDownUrl: String = ""
        var mDownFileName = ""
        var mDownTaskID = -100
    }
}