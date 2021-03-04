package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.ComicDetailsV2
import com.qiuchenly.comicx.Utils.CustomUtils
import com.qiuchenly.comicx.databinding.ComicLocalListBinding
import java.lang.ref.WeakReference

class MyRecentlyAdapter(private var mContext: WeakReference<Context>) :
    BaseRecyclerAdapter<RecentlyReadingBean>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_local_list
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: RecentlyReadingBean, position: Int, ViewType: Int) {
        with(item) {
            val comicLocalList = ComicLocalListBinding.bind(this)
            comicLocalList.bookName.text = data.mComicName
            comicLocalList.currRead.visibility = View.INVISIBLE
            comicLocalList.bookAuthor.text = "来自" + ComicSource.getTypeName(data.mComicType)
            CustomUtils.loadImageCircle(
                this.context,
                data.mComicImageUrl,
                comicLocalList.bookNameImg,
                8
            )
//            val mItem = Comic.getRealm().where(RecentlyReadingBean::class.java).equalTo("BookName", data.BookName!!).findFirst()
//            curr_read.text = if (mItem != null) mItem.BookName_read_point else "无数据"
            setOnClickListener {
                val i = Intent(mContext.get(), ComicDetailsV2::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        mComicString = data.mComicData
                        mComicImg = data.mComicImageUrl
                        mComicType = data.mComicType
                    }))
                })
                mContext.get()?.startActivity(i)
            }
        }
    }
}