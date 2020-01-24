package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.Tools
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.ComicDetailsV2
import com.qiuchenly.comicx.Utils.CustomUtils
import kotlinx.android.synthetic.main.comic_local_list.view.*

class SearchResultAdapter(mCallback: LoaderListener) : BaseRecyclerAdapter<String>() {

    init {
        setLoadMoreCallBack(mCallback)
    }

    override fun canLoadMore() = true

    override fun getItemLayout(viewType: Int) = R.layout.comic_local_list

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            var mImage = ""
            var mAuthor = ""
            var mTitle = ""
            var mCategory = ""

            when (mType) {
                ComicSource.BikaComic -> {
                    val mComicListObject = Gson().fromJson(data, ComicListObject::class.java)
                    mImage = Tools.getThumbnailImagePath(mComicListObject.thumb)
                    mAuthor = mComicListObject.author ?: ""//解决分类中无作者名的问题
                    mTitle = mComicListObject.title
                    val categorys = mComicListObject.categories.joinToString(prefix = "", postfix = ",")
                    mCategory = if (categorys.isNullOrEmpty()) ""
                    else
                        categorys.substring(0, categorys.length - 1)
                }
                ComicSource.DongManZhiJia -> {
                    val mComicListObject = Gson().fromJson(data, ComicHome_CategoryComic::class.java)
                    mImage = mComicListObject.cover
                    mAuthor = mComicListObject.authors
                    mTitle = mComicListObject.title
                    mCategory = mComicListObject.types
                }
            }
            CustomUtils.loadImageCircle(context, mImage, bookNameImg, 8)
            bookName.text = mTitle
            bookAuthor.text = mAuthor
            curr_read.text = "分类:$mCategory"
            setOnClickListener {
                context.startActivity(Intent(context, ComicDetailsV2::class.java).apply {
                    //TODO 需要优化此处
                    putExtra(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        this.mComicType = mType
                        mComicTAG = mCategory
                        when (mType) {
                            ComicSource.DongManZhiJia -> {
                                val mComicListObject = Gson().fromJson(data, ComicHome_CategoryComic::class.java)
                                this.mComicString = Gson().toJson(DataItem().apply {
                                    this.cover = mComicListObject.cover
                                    this.obj_id = mComicListObject.id
                                    this.title = mComicListObject.title
                                    this.status = mComicListObject.status
                                    this.sub_title = mComicListObject.authors
                                    this.type = mComicListObject.types
                                })
                            }
                            else -> {
                                this.mComicImg = mImage
                                this.mComicString = data
                            }
                        }
                    }))
                })
            }
        }
    }

    private var mType = ComicSource.BikaComic
    fun addBikaComic(data: ArrayList<ComicListObject>) {
        setLoadSuccess()
        mType = ComicSource.BikaComic
        data.forEach {
            addData(Gson().toJson(it))
        }
    }


    fun addDMZJComic(list: List<ComicHome_CategoryComic>) {
        setLoadSuccess()
        mType = ComicSource.DongManZhiJia
        list.forEach {
            addData(Gson().toJson(it))
        }
    }
}