package com.qiuchenly.comicx.UI.adapter

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicChapterData
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.ProductModules.Bika.ComicEpisodeObject
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.comic_page_item.view.*

class ComicPageAdapter(mCallback: LoaderListenerEx) :
    BaseRecyclerAdapter<String>() {

    interface LoaderListenerEx : LoaderListener {
        fun goActivity(intent: Intent)
    }

    var canloadMore = true

    override fun setNoMore() {
        super.setNoMore()
        canloadMore = false
    }

    override fun canLoadMore() = canloadMore

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_page_item
    }

    init {
        setLoadMoreCallBack(mCallback)
    }

    private data class Result(val i: String, val title: String)

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        val (intent: String, title) = when (mType) {
            ComicSource.BikaComic -> {
                val mComicEpisodeObject = Gson().fromJson(data, ComicEpisodeObject::class.java)
                Result(
                    Gson().toJson(ComicInfoBean().apply {
                        mComicType = ComicSource.BikaComic
                        mComicID = mBaseID //注意 此处必须设置书籍ID
                        mComicTAG = Gson().toJson(getBaseData())  //设置书籍ID
                        mComicString = position.toString() //设置数据源对应的章节json字符串
                    }),
                    mComicEpisodeObject.title
                )
            }
            ComicSource.DongManZhiJia -> {
                val mComicHomeComicChapterList = Gson().fromJson(data, ComicChapterData::class.java)
                Result(
                    Gson().toJson(ComicInfoBean().apply {
                        mComicType = ComicSource.DongManZhiJia //设置数据源类型
                        mComicID = mBaseID //设置书籍ID
                        mComicTAG = Gson().toJson(getBaseData())  //设置书籍ID
                        mComicString = position.toString() //设置数据源对应的章节json字符串
                    }),
                    mComicHomeComicChapterList.chapter_title
                )
            }
            else -> {
                Result("", "数据有误")
            }
        }
        item.tv_comicPageName.text = title
        item.last_read.visibility = View.GONE
        item.setOnClickListener { view ->
            if (getState() != RecyclerLoadStatus.ON_LOAD_NO_MORE) {
                mCallback?.showMsg("请等待所有章节加载完毕后再试!")
                return@setOnClickListener
            }
            (mCallback as LoaderListenerEx).goActivity(Intent("android.intent.action.ReadPage").apply {
                putExtra(
                    ActivityKey.KEY_CATEGORY_JUMP,
                    intent
                )
            })
        }

    }

    private var mType = ComicSource.BikaComic

    fun setSourceType(mType: Int) {
        this.mType = mType
    }

    private var mBaseID = ""
    fun setBaseID(id: String) {
        mBaseID = id
    }


}