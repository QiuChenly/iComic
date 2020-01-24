package com.qiuchenly.comicx.UI.activity

import android.annotation.SuppressLint
import android.app.Service
import android.content.*
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.*
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.Service.DownloadService
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.adapter.ComicPageAdapter
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.viewModel.ComicDetailsViewModel
import com.qiuchenly.comicx.Utils.CustomUtils
import kotlinx.android.synthetic.main.activity_comicdetails_v2.*
import kotlinx.android.synthetic.main.classic_toolbar.*
import kotlinx.android.synthetic.main.classic_toolbar.view.*
import java.lang.ref.WeakReference

class ComicDetailsV2 :
    BaseApp(),
    ComicDetailContract.View, ComicPageAdapter.LoaderListenerEx {

    override fun goActivity(intent: Intent) {
        startActivity(intent)
    }

    //解决内存泄漏
    private var mAppBarLayout: WeakReference<AppBarLayout>? = null

    override fun onAppBarChange(position: Int) {
        if (position == 0) {
            mAppBarLayout?.get()?.setExpanded(true, true)
        } else {
            mAppBarLayout?.get()?.setExpanded(false, true)
        }
    }

    override fun getLayoutID(): Int? {
        return R.layout.activity_comicdetails_v2
    }

    //==============================   变量声明   ===================================================
    private var mBinder: DownloadService.DownloadBinder? = null

    private var mConn: ServiceConnection? = null

    private lateinit var mViewModel: ComicDetailsViewModel

    private var mComicInfo: ComicListObject? = null

    //==============================   代码整理 界面预设  =============================================

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    //==============================   网络数据回调  =================================================

    override fun onProgressChanged() {

    }

    override fun scrollWithPosition(position: Int) {
    }

    private var mComicTag = "SimpleName|SimpleCode"
    private var mPageChange: ViewPager.OnPageChangeListener? =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                onAppBarChange(position)
            }
        }

    //==============================   常规系统初始化方法  ============================================


    fun setParams(
        description: String,
        title: String,
        status: String,
        chapterSize: Int,
        hotSize: Int,
        mType: String,
        author: String
    ) {
        tv_book_details.text = description
        tv_bookname_title.text = title
        tv_bookname.text = title
        mComicUpdateStatus.text = status
        mNowUpdateSize.text = "已更新${chapterSize}话"
        mHotNum.text = "${hotSize}人气"
        mBookCategoryView.text = mType
        mBookAuthor.text = author
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mConn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                mBinder = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                var msg = "后台下载服务因为发生错误不可用!"
                mBinder = service as DownloadService.DownloadBinder
                if (mBinder == null)
                else {
                    msg = "成功启动后台下载服务"
                    mBinder?.checkThisBookIsDownloadingOrDownload()
                }
                ShowErrorMsg(msg)
            }
        }
        bindService(Intent(this, DownloadService::class.java), mConn!!, Context.BIND_AUTO_CREATE)

        mAppBarLayout = WeakReference(appBarLayout)

        mViewModel = ViewModelProviders.of(this).get(ComicDetailsViewModel::class.java)

        with(mViewModel) {
            message.observe(this@ComicDetailsV2, Observer {
                ShowErrorMsg(it)
                setParams(
                    "拉取漫画数据时服务器出现错误,具体错误为:$it", "漫画数据错误", "未知状态",
                    0, 0, "数据拉取错误", "数据错误"
                )
            })

            mBicaComic.observe(this@ComicDetailsV2, Observer {
                var mType = ""
                it.categories.forEachIndexed { index, s ->
                    mType += "#${s}#" + if (index + 1 == it.categories.size) "" else " "
                }
                val author = it.author
                val chapterSize = it.episodeCount
                val status = "完结状态"
                setParams(
                    "汉化组:${if (it.chineseTeam.isNullOrEmpty()) "无" else it.chineseTeam}\n" + it.description,
                    it.title,
                    status,
                    chapterSize,
                    it.viewsCount,
                    mType,
                    author
                )
            })

            mComicHomeChapter.observe(this@ComicDetailsV2, Observer {
                SetDMZJChapter(it)

                var mType = ""
                it.types.forEachIndexed { index, item ->
                    mType += "#${item.tag_name}#" + if (index + 1 == it.types.size) "" else " "
                }

                var author = ""
                it.authors.forEachIndexed { index, item ->
                    author += item.tag_name + if (index + 1 == it.authors.size) "" else ","
                }

                val status = if (it.status.isNotEmpty()) it.status[0].tag_name else "未知状态"
                val chapterSizeInfo = if (it.chapters.isNotEmpty()) it.chapters[0].data.size else 0

                setParams(
                    it.description,
                    it.title,
                    status,
                    chapterSize = chapterSizeInfo,
                    hotSize = it.hot_num,
                    mType = mType,
                    author = author
                )
            })

            mComicBicaChapterList.observe(this@ComicDetailsV2, Observer {
                SetBikaPages(it)
            })
        }

        comicPageAdas = ComicPageAdapter(this)
        val mListRecyclerView = mChapterList
        mListRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        mListRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.top = 15
                outRect.left = 15
                outRect.right = 15
                outRect.bottom = 15
            }
        })
        mListRecyclerView.adapter = comicPageAdas

        //拿到数据
        val mComicStr = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        //对Intent传来的数据做处理
        if (mComicStr.isNullOrEmpty()) finish()
        val tmpComicInfo = Gson().fromJson(mComicStr, ComicInfoBean::class.java)

        var mComicSourceName = "预设来源"
        var mComicSrc = ""
        var mComicTitle = ""
        var mComicAuthor = ""
        var mBookCategory = "暂无分类"

        mBookCategory = tmpComicInfo.mComicTAG

        mComicTag = "" + tmpComicInfo.mComicType + "|"
        when (tmpComicInfo.mComicType) {
            ComicSource.BikaComic -> {
                mComicSourceName = "哔咔漫画源"
                mComicSrc = tmpComicInfo.mComicImg
                mComicTag += tmpComicInfo.mComicName + "|" + tmpComicInfo.mComicID
                mComicInfo = Gson().fromJson(tmpComicInfo.mComicString, ComicListObject::class.java)
                mComicTitle = mComicInfo?.title ?: "ERROR-数据错误"
                mComicAuthor = mComicInfo?.author ?: "ERROR-数据错误"

                val mComicInfo =
                    Gson().fromJson(tmpComicInfo?.mComicString, ComicDetailObject::class.java)
                mViewModel.getComicInfo(mComicInfo.comicId)

                comicPageAdas?.setSourceType(ComicSource.BikaComic)
                comicPageAdas?.setBaseID(mComicInfo.comicId)
                mViewModel.getComicList(mComicInfo.comicId, 1)
                mComicID = mComicInfo.comicId
            }
            ComicSource.DongManZhiJia -> {
                mComicSourceName = "动漫之家漫画源"
                val mComic = Gson().fromJson(tmpComicInfo.mComicString, DataItem::class.java)

                mComicID = mComic.obj_id

                mComicTag += mComic.title + "|" + mComic.obj_id
                mComicSrc = mComic.cover
                mComicAuthor = mComic.sub_title
                mComicTitle = mComic.title

                comicPageAdas?.setSourceType(ComicSource.DongManZhiJia)
                comicPageAdas?.setBaseID(mComic.obj_id)
                comicPageAdas?.setNoMore()//漫画之家的漫画章节似乎是直接返回所有的.
                mViewModel.getComicHomeComicChapter(mComic.obj_id)
            }
        }


        mAddFavorite.setOnClickListener {
            val book = mViewModel.comicExist(mComicTitle)
            if (book == null) {
                mViewModel.comicAdd(LocalFavoriteBean().apply {
                    this.mComicName = mComicTitle
                    this.mComicImageUrl = mComicSrc
                    this.mComicData = tmpComicInfo.mComicString
                    this.mComicType = tmpComicInfo.mComicType
                    this.mComicLastReadTime = System.currentTimeMillis()
                })
                ShowErrorMsg("已加入本地图书列表！")
                mAddFavorite.text = "取消收藏"
            } else {
                mViewModel.comicDel(book.mComicName)
                ShowErrorMsg("移除成功！")
                mAddFavorite.text = "收藏"
            }
        }

        mStartRead_ContinueReading.setOnClickListener {
            val bin = Intent(this, ReadPage::class.java)
//            var link = lastReadPageUrl
            ContextCompat.startActivity(this, bin, null)
        }


        //TODO 此处需要修复以供开始阅读按钮使用
        val book = mViewModel.comicExist(mComicTitle)
        if (book?.mComicData?.isNotEmpty() == true) {
            mAddFavorite.text = "取消收藏"
        }

        val point =
            null//realm.where(ComicBookInfo_Recently::class.java).equalTo("ComicName", mComicInfo?.ComicName).findFirst()
        if (point != null) {
            mStartRead_ContinueReading.text = "继续阅读"
        }

        //=================  初始化界面数据  ===================
        CustomUtils.loadImage(this, mComicSrc, mRealImageNoBlur, 0, 20)
        CustomUtils.loadImage(this, mComicSrc, comicDetails_img, 10, 20)
        CustomUtils.loadImage(this, mComicSrc, mCardView, 30, 20)
        //CustomUtils.loadImage(this, mComicSrc, mToolbarBack, 50, 20)


        mBookAuthor.text = mComicAuthor
        tv_bookname_title_small.text = mComicSourceName
        mBookCategoryView.text = "#$mBookCategory#"
        tv_book_details.text = ""

        toolbar_up.mTitleLayout.tv_bookname_title.text = mComicTitle
        toolbar_up.classic_toolbar.setBackgroundColor(Color.BLACK)
        toolbar_up.back.setBackgroundColor(Color.BLACK)

        toolbar.mTitleLayout.alpha = 0f
        //此处实现淡入淡出效果
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val mCurrentPercents = (-verticalOffset * 1f) / appBarLayout.totalScrollRange / 0.2f

            toolbar_up.alpha = mCurrentPercents
            mRealImageNoBlur.alpha = 1f - mCurrentPercents
            println(verticalOffset)
            if (
                verticalOffset < 0
            ) {
                toolbar.visibility = View.INVISIBLE
            } else {
                toolbar.visibility = View.VISIBLE
            }
            mPullView.setCanPull(verticalOffset > -10)
            //mTitleLayout.alpha = mCurrentPercents
        })
        back_up.setOnClickListener { finish() }
        mShareButton.setOnClickListener {
            val mClipboardManager = getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
            mClipboardManager.setPrimaryClip(ClipData.newPlainText("text", mComicTag))
            ShowErrorMsg("已复制漫画相关信息")
        }


        mBookDownload.setOnClickListener {
            mBinder?.download(tmpComicInfo, this)
        }


//        mAdapter = SuperPagerAdapter(supportFragmentManager, mFragmentsList)
//        mComicInfoViewPager.adapter = mAdapter
//        BaseNavigatorCommon.setUpWithPager(
//            this,
//            mFragmentsList,
//            magic_indicator,
//            mComicInfoViewPager
//        )
//
//        mComicInfoViewPager.addOnPageChangeListener(mPageChange!!)


        //数据插入
        val mRecentlyReadingBean = RecentlyReadingBean().apply {
            this.mComicName = mComicTitle
            if (mComicName.isEmpty())
                Throwable("准备插入数据时发现数据为空.")
            this.mComicImageUrl = mComicSrc
            this.mComicType = tmpComicInfo.mComicType
            this.mComicData = tmpComicInfo.mComicString
            this.mComicLastReadTime = System.currentTimeMillis()
        }
        Comic.getRealm()?.executeTransaction {
            it.copyToRealmOrUpdate(mRecentlyReadingBean)
        }
    }

    var mComicID = ""
    override fun onLoadMore(isRetry: Boolean) {
        pageSize++
        comicPageAdas?.setBaseID(mComicID)
        mViewModel.getComicList(mComicID, pageSize)
    }

    fun loadFailure(t: Throwable) {
        comicPageAdas?.setLoadFailed()
    }

    var pageSize = 1
    private var comicPageAdas: ComicPageAdapter? = null

    fun SetDMZJChapter(docs: ComicHomeComicChapterList) {
        if (docs.chapters.isNotEmpty()) {
            docs.chapters.forEach {
                comicPageAdas?.addData(getArr2Str(ArrayList(it.data)))
            }
        } else {
            ShowErrorMsg("该漫画还没有上传任何章节!")
        }
    }

    fun SetBikaPages(docs: ComicEpisodeResponse) {
        docs.eps.docs ?: return
        comicPageAdas?.addData(getArr2Str(docs.eps.docs))
        if (docs.eps.page == docs.eps.pages) {
            comicPageAdas?.setNoMore()
        } else {
            //自动加载所有的漫画章节
        }
    }

    private fun <T> getArr2Str(clazz: ArrayList<T>): ArrayList<String> {
        val mArr = ArrayList<String>()
        clazz.forEach {
            mArr.add(Gson().toJson(it))
        }
        return mArr
    }

    //获取单一实例

    override fun onDestroy() {
        super.onDestroy()
        if (mConn != null) {
            unbindService(mConn!!)
            mConn = null
        }
//        mComicInfoViewPager.removeOnPageChangeListener(mPageChange!!)
        mPageChange = null
        mBinder = null
    }
}