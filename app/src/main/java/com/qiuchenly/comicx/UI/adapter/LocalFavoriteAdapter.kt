package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.Tools
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRealmRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.ComicDetailsV2
import com.qiuchenly.comicx.Utils.CustomUtils
import io.realm.RealmResults
import kotlinx.android.synthetic.main.comic_local_list.view.*
import java.lang.ref.WeakReference

class LocalFavoriteAdapter
    (
    mComics: RealmResults<LocalFavoriteBean>,
    private var mContext: WeakReference<Context>
) : BaseRealmRecyclerAdapter<LocalFavoriteBean>() {

    override fun canLoadMore() = false
    override fun getItemLayout(viewType: Int) = R.layout.comic_local_list

    init {
        setData(mComics)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: LocalFavoriteBean?, position: Int, ViewType: Int) {
        if (data == null) return
        val itemData = data.mComicData
        var mBookName = ""
        var mBookAuthor = ""
        var mBookImage = ""
        with(item) {
            when (data.mComicType) {
                ComicSource.BikaComic -> {
                    val mComicInfo = Gson().fromJson(itemData, ComicListObject::class.java)
                    mBookName = mComicInfo.title
                    mBookAuthor = mComicInfo.author
                    mBookImage = Tools.getThumbnailImagePath(mComicInfo.thumb)
                }
                ComicSource.DongManZhiJia -> {
                    val mComicInfo = Gson().fromJson(itemData, DataItem::class.java)
                    mBookName = "${mComicInfo.title}(${mComicInfo.status})"
                    mBookAuthor = mComicInfo.sub_title
                    mBookImage = mComicInfo.cover
                }
            }
            bookName.text = mBookName
            bookAuthor.text = mBookAuthor
            curr_read.text = ComicSource.getTypeName(data.mComicType)
            CustomUtils.loadImageCircle(item.context, mBookImage, item.bookNameImg, 8)
            setOnClickListener {
                if (BikaApi.getAPI() == null && data.mComicType == ComicSource.BikaComic) {
                    Toast.makeText(Comic.getContext(), "请先打开哔咔页面以初始化哔咔服务器!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val i = Intent(mContext.get(), ComicDetailsV2::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        mComicString = itemData
                        mComicImg = mBookImage
                        mComicType = data.mComicType
                    }))
                })
                mContext.get()?.startActivity(i)
            }
        }
    }
}