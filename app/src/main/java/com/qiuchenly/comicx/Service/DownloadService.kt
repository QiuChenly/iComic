package com.qiuchenly.comicx.Service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.*
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.ApiService
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.Tools
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.view.ReaderContract
import com.qiuchenly.comicx.UI.viewModel.ComicDetailsViewModel
import com.qiuchenly.comicx.UI.viewModel.ReadViewModel
import com.qiuchenly.comicx.Utils.CustomUtils
import io.realm.RealmResults
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 后台下载服务类,实现后台下载并将数据保存到本地数据库
 * @author 秋城落叶
 * @sample \NM$L
 */
class DownloadService : Service(), ServiceNotification {

    private var TAG = "DownloadService"

    fun log(str: String) {
        Log.d(TAG, str)
    }

    //暂时隐藏其余代码
    override fun onBookPageWasDown(bookName: String, title: String): Boolean {
        val book = mRealm?.where(DownloadBookInfo::class.java)
            ?.equalTo("BookName", bookName)
            ?.findFirst()
        if (book != null) {
            return try {
                book.PageList.filter { it.titleName == title }[0].DownOver
            } catch (e: Exception) {
                false
            }
        }
        return false
    }

    override fun onDownBookOver(bookName: String) {
        val mBookInfo = mRealm?.where(DownloadBookInfo::class.java)
            ?.equalTo("BookName", bookName)
            ?.findFirst()
        if (mBookInfo != null) {
            mRealm?.beginTransaction()
            mBookInfo.DownOver = true
            mRealm?.commitTransaction()
        }
    }

    /**
     * 获取所有本地书籍书
     * @return 从数据库返回所有数据
     */
    override fun onGetAllDownBook(): RealmResults<DownloadBookInfo>? {
        return mRealm?.where(DownloadBookInfo::class.java)
            ?.findAll()
    }

    /**
     * 将新书书籍加入本地数据库
     * @param mDownloadBookInfo 新书数据集合
     */
    override fun onBookAdded(mDownloadBookInfo: DownloadBookInfo) {
        mRealm?.beginTransaction()
        mRealm?.copyToRealm(mDownloadBookInfo)
        mRealm?.commitTransaction()
    }

    /**
     * 检查下载本地数据库中是否存在此书
     * @param mBookName 书名
     * @return true or false
     */
    override fun onBookHasInDataBase(mBookName: String): Boolean {
        return mRealm?.where(DownloadBookInfo::class.java)
            ?.equalTo("BookName", mBookName)
            ?.findFirst() == null
    }

    override fun onSaveBookPage(mBookName: String, mPageInfo: PageInfo) {
        /* {
             val mBook = mRealm.where(DownloadBookInfo::class.java)
                     .equalTo("BookName", mBookName).findFirst()
             if (mBook != null) {
                 mRealm.beginTransaction()
                 mBook.PageList.add(mPageInfo.apply {
                     DownOver = true
                 })
                 mRealm.commitTransaction()
             }
         }*/

    }

    override fun onMessage(title: String, content: String, NoticeID: Int) {
        CustomUtils.SendNotificationEx(this, title, content, NoticeID)
    }

    private val mRealm = Comic.getRealm()
    private var mBinder: DownloadBinder? = null
    override fun onBind(intent: Intent) = mBinder

    override fun onCreate() {
        super.onCreate()
        mBinder = DownloadBinder(this, applicationContext)
        mBinder?.getDataBaseBook()
        mBinder?.startResumeDownThread()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinder?.mThreadPool?.shutdownNow()
    }

    class DownloadBinder(
        private val mServiceNotification: ServiceNotification,
        context: Context?
    ) : Binder(), ComicDetailContract.Comiclist.View {


        private var comicMap = HashMap<String, DownloadBookInfo>()
        override fun SetBikaPages(docs: ComicEpisodeResponse?) {
//            docs?.eps?.docs?.forEach { data ->
//                comicMap[id]?.PageList?.add(PageInfo().apply {
//                    this.titleName = data.title
//                    order = data.order
//                    chapterID = data.episodeId
//                })
//            }
//            if (docs?.eps?.page != docs?.eps?.pages) {
//                bikaComicListViewModel?.getComicList(id, (docs?.eps?.page ?: 0) + 1)
//                return
//            }
//            //comicMap.remove(id)
//            if (comicMap[id] == null) return
//            val ret = ArrayList<PageInfo>()
//            ret.apply {
//                addAll(comicMap[id]!!.PageList.toList())
//            }.sortBy {
//                it.order
//            }
//            comicMap[id]!!.PageList.apply {
//                clear()
//                addAll(ret)
//            }
//            mThreadPool.execute(object : DownThread(
//                comicMap[id]!!,
//                mServiceNotification
//            ) {
//                override fun onThreadOver() {
//                    mCacheThread.remove(this)
//                }
//
//                override fun onThreadStart() {
//                    mCacheThread.add(this)
//                }
//            })
//            view?.ShowErrorMsg("开始下载...")
        }

        //暂时隐藏部分代码
        override fun SetDMZJChapter(docs: ComicHomeComicChapterList) {

        }

        override fun loadFailure(t: Throwable) {

        }

        override fun ShowErrorMsg(msg: String) {

        }

        private var mList = ArrayList<DownloadBookInfo>()
        val mThreadPool = Executors.newFixedThreadPool(3) as ExecutorService
        val mCacheThread = ArrayList<DownThread>()
        private var mContext: Context? = context

        fun getDataBaseBook() {
            mList = ArrayList(mServiceNotification.onGetAllDownBook()!!)
        }

        fun startResumeDownThread() {
            mList.forEach {
                if (!it.DownOver) {
                    download(ComicInfoBean(), null)
                }
            }
        }

        /**
         * 检查内存缓存内的下载列表内存在此任务
         * @param comicInfo 漫画书籍信息
         * @return 逻辑值，成功返回真
         */
        fun hasBookInList(comicInfo: ComicInfoBean) =
            mList.any { it.BookName == comicInfo.mComicName }

        private var bikaHttpInstance: ApiService? = null
        private var bikaComicListViewModel: ComicDetailsViewModel? = null

        /**
         * 新建一个下载任务
         * @param comicInfo 下载漫画信息
         * @param view 前台UI回调信息
         * @return 无返回信息,所有返回均在回调内
         */
        fun download(comicInfo: ComicInfoBean, view: ComicDetailContract.View?) {
            //分类处理
            when (comicInfo.mComicType) {
                ComicSource.BikaComic -> {
                    if (bikaHttpInstance == null) {
                        if (BikaApi.getAPI() == null)
                            BikaApi.setBiCaClient(mContext!!)
                        bikaHttpInstance = BikaApi.getAPI()
                        bikaComicListViewModel = ComicDetailsViewModel()
                    }

                    val mComicInfo =
                        Gson().fromJson(comicInfo.mComicString, ComicListObject::class.java)
                    comicMap[comicInfo.mComicID] = DownloadBookInfo().apply {
                        Author = mComicInfo.author
                        BookCategory.addAll(mComicInfo.categories)
                        Booklink = mComicInfo.comicId
                        BookName = mComicInfo.title
                        BookImage = Tools.getThumbnailImagePath(mComicInfo.thumb)
                        BookSource = comicInfo.mComicType
                    }
                    bikaComicListViewModel?.getComicList(comicInfo.mComicID, 1)
                    println("?")
                }
                ComicSource.DongManZhiJia -> {

                }
                else -> {

                }
            }

            /* bikaHttpInstance?.Activity_ComicModel().InitPageInfo(
                 comicInfo.BookLink!!,
                 object : ComicDetailContract.GetPageInfo {
                     override fun onFailed(reasonStr: String) {
                         mServiceNotification.onMessage(
                             "下载出错",
                             "无法获取该漫画的详细数据:" + comicInfo.BookName,
                             NotificationType.TYPE_ERROR_MSG
                         )
                         view?.ShowErrorMsg("下载出错:请检查该漫画数据是否正常。")
                     }

                     override fun onSuccessGetInfo(
                         author: String,
                         updateTime: String,
                         hits: String, category: String,
                         introduction: String,
                         retPageList: ArrayList<ComicBookInfo>
                     ) {
                         val realList = CustomUtils.sort(
                             1,//排序正向
                             retPageList
                         )
                         if (mServiceNotification.onBookHasInDataBase(comicInfo.BookName!!)) {
                             val arr = DownloadBookInfo().apply {
                                 BookName = comicInfo.BookName!!
                                 realList.forEach {
                                     this.PageList.add(PageInfo().apply {
                                         this.titleName = it.title!!
                                     })
                                 }
                                 Booklink = comicInfo.BookLink!!
                             }

                             if (!mList.any {
                                     val ret = it.BookName == arr.BookName
                                     var count = arr.PageList.size - it.PageList.size
                                     if (count > 0) {
                                         while (count > 0) {
                                             it.PageList.add(it.PageList.size, arr.PageList[count])
                                             count--
                                         }
                                     }
                                     ret
                                 }) {
                                 mServiceNotification.onBookAdded(arr)
                                 mList.add(arr)
                             } else {
                                 // mList.get()
                             }

                             mThreadPool.execute(object : DownThread(
                                 comicInfo.BookName!!,
                                 realList,
                                 mServiceNotification
                             ) {
                                 override fun onThreadOver() {
                                     mCacheThread.remove(this)
                                 }

                                 override fun onThreadStart() {
                                     mCacheThread.add(this)
                                 }
                             })
                             view?.ShowErrorMsg("开始下载...")
                         }
                     }
                 })*/
        }

        fun getDownloadComicSize() {

        }

        fun checkThisBookIsDownloadingOrDownload() {

        }
    }

    /**
     * 下载任务线程
     * @author QiuChenly
     */
    abstract class DownThread(
        private val Book: DownloadBookInfo,
        private val mServiceNotification: ServiceNotification
    ) : Thread(), ReaderContract.View {
        override fun ShowErrorMsg(msg: String) {

        }

        override fun onFailed(reasonStr: String) {

        }

        override fun onLoadSucc(
            lst: ArrayList<String>,
            next: String,
            currInfo: String,
            isOver: Boolean
        ) {

        }

        /**
         * 下载结束后的回调
         */
        abstract fun onThreadOver()

        /**
         * 下载开始时候的回调
         */
        abstract fun onThreadStart()


        private var mBookID = System.currentTimeMillis().toString().substring(7, 13).toInt()
        private var mIsDown = false
        private val mFileName = when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.Q -> Comic.getContext()?.getExternalFilesDir(null)?.path
            else -> Environment.getExternalStorageDirectory().path
        } + "/ComicParseReader/"

        private var mReadViewModel: ReadViewModel? = null

        private val TAG = "QiuChen"
        override fun run() {

            mReadViewModel = ReadViewModel(this)

            onThreadStart()

            val fileParent = File(mFileName)
            if (!fileParent.exists()) {
                fileParent.mkdirs()
            }

            var i = 0
            while (true) {
                mReadViewModel?.getBikaImage(Book.Booklink, i++)
            }

            mServiceNotification.onDownBookOver(Book.BookName)
            mServiceNotification.onMessage(
                "下载结束:${Book.BookName}",
                "下载完成!",
                mBookID
            )
            onThreadOver()
            mReadViewModel = null
        }
    }
}