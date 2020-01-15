package com.qiuchenly.comicx.UI.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicChapterData
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.ProductModules.Bika.ComicEpisodeObject
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.ReadPage
import kotlinx.android.synthetic.main.comic_page_item.view.*

class ComicPageAdapter(private var mContext: Context?, mCallback: LoaderListener) :
    BaseRecyclerAdapter<String>() {

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

    fun clearContext() {
        mContext = null
    }

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        when (mType) {
            ComicSource.BikaComic -> {
                val mComicEpisodeObject = Gson().fromJson(data, ComicEpisodeObject::class.java)
                item.tv_comicPageName.text = mComicEpisodeObject.title
                item.last_read.visibility = View.GONE
                item.setOnClickListener {
                    if (getState() != RecyclerLoadStatus.ON_LOAD_NO_MORE) {
                        Toast.makeText(mContext, "请等待所有章节加载完毕后再试!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    mContext?.startActivity(Intent(mContext, ReadPage::class.java).apply {
                        putExtra(
                            ActivityKey.KEY_CATEGORY_JUMP,
                            Gson().toJson(ComicInfoBean().apply {
                                mComicType = ComicSource.BikaComic
                                mComicID = mBaseID //注意 此处必须设置书籍ID
                                mComicTAG = Gson().toJson(getBaseData())  //设置书籍ID
                                mComicString = position.toString() //设置数据源对应的章节json字符串
                            })
                        )
                    })
                }
            }
            ComicSource.DongManZhiJia -> {
                val mComicHomeComicChapterList = Gson().fromJson(data, ComicChapterData::class.java)
                item.tv_comicPageName.text = mComicHomeComicChapterList.chapter_title
                item.last_read.visibility = View.GONE
                item.setOnClickListener(null)
                item.setOnClickListener {
                    mContext?.startActivity(Intent(mContext, ReadPage::class.java).apply {
                        putExtra(
                            ActivityKey.KEY_CATEGORY_JUMP,
                            Gson().toJson(ComicInfoBean().apply {
                                mComicType = ComicSource.DongManZhiJia //设置数据源类型
                                mComicID = mBaseID //设置书籍ID
                                mComicTAG = Gson().toJson(getBaseData())  //设置书籍ID
                                mComicString = position.toString() //设置数据源对应的章节json字符串
                            })
                        )
                    })
                }
            }
            else -> {

            }
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