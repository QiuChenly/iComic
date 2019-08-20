package com.qiuchenly.comicx.UI.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import com.qiuchenly.comicx.Bean.ComicChapterData
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.ComicEpisodeObject
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.adapter.ComicReadingAdapter
import com.qiuchenly.comicx.UI.view.ReaderContract
import com.qiuchenly.comicx.UI.viewModel.ReadViewModel
import com.qiuchenly.comicx.Utils.BuilderManager
import kotlinx.android.synthetic.main.activity_reader_page.*
import java.lang.ref.WeakReference


class ReadPage : BaseApp(), ReaderContract.View, BaseRecyclerAdapter.LoaderListener {
    private var lastPoint = 0

    override fun showMsg(str: String) = ShowErrorMsg(str)

    override fun onLoadMore(isRetry: Boolean) {
        if (nextUrl.isNotEmpty()) {
            //TODO 此处加载下一页
            when (mTempComicInfo!!.mComicType) {
                ComicSource.DongManZhiJia -> {
                    mViewModel?.getDMZJImage(bookID, nextUrl)
                }
                ComicSource.BikaComic -> {
                    mViewModel?.getBikaImage(bookID, nextUrl.toInt())
                }
            }
        } else {
            mComicImagePageAda?.setNoMore()
        }
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onFailed(reasonStr: String) {
        ShowErrorMsg(reasonStr)
        mComicImagePageAda?.setLoadFailed()
    }

    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String, isOver: Boolean) {
        mComicImagePageAda?.setLoadSuccess()
        nextUrl = next
        if (isOver) {
            onLoadMore(true)
            return
        }
        mPoint++
        val chapter = when (mTempComicInfo!!.mComicType) {
            ComicSource.DongManZhiJia -> {
                nextUrl = if (mPoint < mDMZJChapter!!.size) {
                    mDMZJChapter!![mPoint].chapter_id
                } else {
                    ""
                }
                mDMZJChapter!![mPoint - 1].chapter_title
            }
            ComicSource.BikaComic -> {
                if (mPoint - 1 <= mBikaChapter!!.size)
                    nextUrl = if (mPoint < mBikaChapter!!.size) {
                        mBikaChapter!![mPoint].order.toString()
                    } else {
                        ""
                    }
                mBikaChapter!![mPoint - 1].title
            }
            else -> {
                "数据源加载错误!"
            }
        }
        currInfos.text = chapter

        lastPoint = mComicImagePageAda?.itemCount!!
        mComicImagePageAda?.addData(lst)
        mAppBarComicReader.setExpanded(true, true)
        Snackbar.make(read_page_coordinator_layout, "注意:当前已阅读到 $chapter .", Snackbar.LENGTH_SHORT)
            .show()
        //currInfos.text = currInfo
        if (lastPoint < 0) {
            rv_comicRead_list.smoothScrollToPosition(lastPoint)
        }
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return com.qiuchenly.comicx.R.layout.activity_reader_page
    }

    private var currUrl = ""
    private var mComicImagePageAda: ComicReadingAdapter? = null
    private var curr = -1

    var bookID = ""
    var order = 1

    private fun getArr2Str(clazz: ArrayList<String>): ArrayList<ComicChapterData> {
        val mArr = ArrayList<ComicChapterData>()
        clazz.forEach {
            mArr.add(Gson().fromJson(it, ComicChapterData::class.java))
        }
        return mArr
    }

    private fun getArr2StrA(clazz: ArrayList<String>): ArrayList<ComicEpisodeObject> {
        val mArr = ArrayList<ComicEpisodeObject>()
        clazz.forEach {
            mArr.add(Gson().fromJson(it, ComicEpisodeObject::class.java))
        }
        return mArr
    }

    private var mViewModel: ReadViewModel? = null
    private var mDMZJChapter: ArrayList<ComicChapterData>? = null
    private var mBikaChapter: ArrayList<ComicEpisodeObject>? = null
    private var mPoint = 0
    private var mTempComicInfo: ComicInfoBean? = null

    var realm = WeakReference(Comic.getRealm())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ReadViewModel(this)
        val mStr = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        mTempComicInfo = Gson().fromJson(mStr, ComicInfoBean::class.java)

        if (mTempComicInfo?.mComicID == null) {
            ShowErrorMsg("漫画ID不可以是NULL!请联系作者查找bug")
            finish()
            return
        }

        bookID = mTempComicInfo?.mComicID ?: ""
        mPoint = mTempComicInfo?.mComicString?.toInt() ?: 0
        mComicImagePageAda = ComicReadingAdapter(this, WeakReference(this))
        rv_comicRead_list.layoutManager = LinearLayoutManager(this).apply {
            //isAutoMeasureEnabled = true
            //isSmoothScrollbarEnabled = true
        }
        rv_comicRead_list.setHasFixedSize(true)
        rv_comicRead_list.adapter = mComicImagePageAda
        //rv_comicRead_list.screenWidth = DisplayUtil.getScreenWidth(Comic.getContext())
        rv_comicRead_list.isEnableScale =
            true //感谢这个作者的开源项目。https://github.com/PortgasAce/ZoomRecyclerView/blob/master/demo/src/main/java/com/portgas/view/demo/MainActivity.java
        rv_comicRead_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //优化RV滑动时加载图片导致的卡顿
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //Glide.with(this@ReadPage).resumeRequests()

                } else {
                    //Glide.with(this@ReadPage).pauseRequests()
                }
            }
        })

        assert(bmb != null)
        bmb.buttonEnum = ButtonEnum.Ham
        bmb.piecePlaceEnum = PiecePlaceEnum.HAM_5
        bmb.buttonPlaceEnum = ButtonPlaceEnum.HAM_5
        BuilderManager.getHamButtonBuilder(bmb)

        //=============  初始化界面数据  ===============
        when (mTempComicInfo!!.mComicType) {
            ComicSource.DongManZhiJia -> {
                mDMZJChapter = getArr2Str(
                    Gson().fromJson(
                        mTempComicInfo!!.mComicTAG,
                        ArrayList<String>()::class.java
                    )
                )
                mDMZJChapter?.reverse()
                mPoint = (mDMZJChapter?.size ?: 0) - mPoint - 1
                val mBase = mDMZJChapter?.get(mPoint)
                currInfos.text = mBase?.chapter_title
                mViewModel?.getDMZJImage(bookID, mBase!!.chapter_id)
            }
            ComicSource.BikaComic -> {
                mBikaChapter = getArr2StrA(
                    Gson().fromJson(
                        mTempComicInfo!!.mComicTAG,
                        ArrayList<ComicEpisodeObject>()::class.java
                    ) as ArrayList<String>
                )
                mBikaChapter?.reverse()
                mPoint = (mBikaChapter?.size ?: 0) - mPoint - 1
                val mBase = mBikaChapter?.get(mPoint)
                currInfos.text = mBase?.title
                mViewModel?.getBikaImage(bookID, mBase!!.order)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
        mComicImagePageAda = null
        finish()
    }
}