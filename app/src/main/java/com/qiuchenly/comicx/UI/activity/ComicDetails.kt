package com.qiuchenly.comicx.UI.activity

import android.annotation.SuppressLint
import android.app.Service
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.Service.DownloadService
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicx.UI.BaseImp.SuperPagerAdapter
import com.qiuchenly.comicx.UI.fragment.ComicBasicInfo
import com.qiuchenly.comicx.UI.fragment.ComicList
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.viewModel.ComicDetailsViewModel
import com.qiuchenly.comicx.Utils.CustomUtils
import kotlinx.android.synthetic.main.activity_comicdetails.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*
import java.lang.ref.WeakReference

class ComicDetails :
    BaseApp(),
    ComicDetailContract.View {

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
        return R.layout.activity_comicdetails
    }

    override fun onLoading() {
        onFailed?.visibility = View.INVISIBLE
        onSuccess?.visibility = View.INVISIBLE
        onFailed?.isClickable = false
        onLoading?.visibility = View.VISIBLE
    }

    override fun onLoadSuccess() {
        onFailed?.visibility = View.INVISIBLE
        onSuccess?.visibility = View.VISIBLE
        onLoading?.visibility = View.INVISIBLE
        onFailed?.isClickable = false
    }


    override fun onLoadFailed() {
        onFailed?.isClickable = true
        onFailed?.setOnClickListener {

        }
        onFailed?.visibility = View.VISIBLE
        onSuccess?.visibility = View.INVISIBLE
        onLoading?.visibility = View.INVISIBLE
    }

    //==============================   变量声明   ===================================================
    private var mBinder: DownloadService.DownloadBinder? = null

    private var mConn: ServiceConnection? = null

    private var mViewModel: ComicDetailsViewModel? = null

    private var onSuccess: CoordinatorLayout? = null
    private var onFailed: TextView? = null
    private var onLoading: ProgressBar? = null

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
        if (mAdapter != null) {
            val mFragment = mAdapter?.getInstance("章节") as ComicList
//            mFragment.scrollWithPosition(position)
        }
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
                Toast.makeText(this@ComicDetails, msg, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        bindService(Intent(this, DownloadService::class.java), mConn!!, Context.BIND_AUTO_CREATE)

        mViewModel = ComicDetailsViewModel(this)

        onLoading = load_ing
        onFailed = load_failed
        onSuccess = mCoordinatorLayout

        mAppBarLayout = WeakReference(appBarLayout)

        //对Intent传来的数据做处理
        val mComicStr = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        if (mComicStr.isNullOrEmpty()) finish()
        val baseInfo = Gson().fromJson(mComicStr, ComicInfoBean::class.java)

        var mComicSourceName = "预设来源"
        var mComicSrc = ""
        var mComicTitle = ""
        var mComicAuthor = ""
        var mBookCategory = "暂无分类"

        mBookCategory = baseInfo.mComicTAG

        mComicTag = "" + baseInfo.mComicType + "|"
        when (baseInfo.mComicType) {
            ComicSource.BikaComic -> {
                mComicSourceName = "哔咔漫画源"
                mComicSrc = baseInfo.mComicImg
                mComicTag += baseInfo.mComicName + "|" + baseInfo.mComicID
                mComicInfo = Gson().fromJson(baseInfo.mComicString, ComicListObject::class.java)
                mComicTitle = mComicInfo?.title ?: "ERROR-数据错误"
                mComicAuthor = mComicInfo?.author ?: "ERROR-数据错误"
            }
            ComicSource.DongManZhiJia -> {
                mComicSourceName = "动漫之家漫画源"
                val mComic = Gson().fromJson(baseInfo.mComicString, DataItem::class.java)
                mComicTag += mComic.title + "|" + mComic.obj_id
                mComicSrc = mComic.cover
                mComicAuthor = mComic.sub_title
                mComicTitle = mComic.title
            }
        }
        onLoadSuccess()
        //=================  初始化界面数据  ===================
        CustomUtils.loadImage(this, mComicSrc, mRealImageNoBlur, 10, 20)
        CustomUtils.loadImageCircle(this, mComicSrc, iv_comic_image, 8)
        CustomUtils.loadImage(this, mComicSrc, comicDetails_img, 10, 20)

        tv_bookname_title.text = mComicTitle
        tv_bookname.text = mComicTitle
        mBookAuthor.text = "来源:$mComicAuthor"
        tv_bookname_title_small.text = mComicSourceName
        mBookCategoryView.text = "类别:$mBookCategory"
        tv_book_details.text = ""

        val mFragmentsList = arrayListOf(
            SuperPagerAdapter.Struct("简介", ComicBasicInfo().apply {
                setUI(baseInfo)
            }),
            SuperPagerAdapter.Struct("章节", ComicList().apply {
                if (baseInfo.mComicID == "" && baseInfo.mComicType == ComicSource.BikaComic) {
                    baseInfo.mComicID = mComicInfo!!.comicId
                    //这里是为了解决加载哔咔数据源时漫画ID为空的问题，类别：官方汉化
                }
                setUI(baseInfo)
            })
        )
        comicDetails_img.alpha = 0f
        //mTitleLayout.alpha = 0f
        //此处实现淡入淡出效果
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val mCurrentPercents = (-verticalOffset * 1f) / appBarLayout.totalScrollRange

            comicDetails_img.alpha = mCurrentPercents//实现渐变模糊特效
            rl_comic_content.alpha = 1f - mCurrentPercents
            //mTitleLayout.alpha = mCurrentPercents
        })
        back_up.setOnClickListener { finish() }
        mShareButton.setOnClickListener {
            val mClipboardManager = getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
            mClipboardManager.setPrimaryClip(ClipData.newPlainText("text", mComicTag))
            ShowErrorMsg("已复制漫画相关信息")
        }


        mBookDownload.setOnClickListener {
            mBinder?.download(baseInfo, this)
        }


        mAdapter = SuperPagerAdapter(supportFragmentManager, mFragmentsList)
        mComicInfoViewPager.adapter = mAdapter
        BaseNavigatorCommon.setUpWithPager(
            this,
            mFragmentsList,
            magic_indicator,
            mComicInfoViewPager
        )

        mComicInfoViewPager.addOnPageChangeListener(mPageChange!!)


        //数据插入
        val mRecentlyReadingBean = RecentlyReadingBean().apply {
            this.mComicName = mComicTitle
            if (mComicName.isEmpty())
                Throwable("准备插入数据时发现数据为空.")
            this.mComicImageUrl = mComicSrc
            this.mComicType = baseInfo.mComicType
            this.mComicData = baseInfo.mComicString
            this.mComicLastReadTime = System.currentTimeMillis()
        }
        Comic.getRealm()?.executeTransaction {
            it.copyToRealmOrUpdate(mRecentlyReadingBean)
        }
    }

    //获取单一实例
    private var mAdapter: SuperPagerAdapter? = null

    override fun onDestroy() {
        super.onDestroy()
        if (mConn != null) {
            unbindService(mConn!!)
            mConn = null
        }
        mComicInfoViewPager.removeOnPageChangeListener(mPageChange!!)
        mPageChange = null
        mViewModel?.cancel()
        mViewModel = null
        mBinder = null
        mAdapter = null
        onLoading = null
        onFailed = null
        onSuccess = null
    }
}