package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.Tools
import com.qiuchenly.comicx.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseViewHolder
import com.qiuchenly.comicx.UI.activity.BrowserView
import com.qiuchenly.comicx.UI.activity.RecentlyRead
import com.qiuchenly.comicx.UI.activity.SearchResult
import com.qiuchenly.comicx.UI.adapter.BiKaDataAdapter.ItemType.BICA_ACCOUNT
import com.qiuchenly.comicx.UI.adapter.BiKaDataAdapter.ItemType.BICA_COMIC_TYPE
import com.qiuchenly.comicx.UI.view.BikaInterface
import com.qiuchenly.comicx.Utils.CustomUtils
import kotlinx.android.synthetic.main.dialog_switchweb.view.*
import kotlinx.android.synthetic.main.item_bika_userinfo.view.*
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import java.lang.ref.WeakReference


class BiKaDataAdapter(
    private val mViews: BikaInterface,
    private var mContext: WeakReference<Context>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    //the first item must be an account information.

    val layout_account = R.layout.item_bika_userinfo
    val layout_category = R.layout.item_foosize_newupdate

    private var mItems = ArrayList<CategoryObject>()

    override fun getItemCount(): Int {
        return mItems.size + 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            BICA_ACCOUNT -> userProfileSet(holder.itemView)
            BICA_COMIC_TYPE -> categorySet(holder.itemView, mItems[position - 1])
        }
    }

    fun getSpanWithPosition(position: Int): Int {
        return when (getItemViewType(position)) {
            BICA_ACCOUNT -> 6
            else -> 2
        }
    }


    val KEY_PICA_APP_NAME = "KEY_PICA_APP_NAME"
    val KEY_PICA_APP_URL = "KEY_PICA_APP_URL"
    val TAG = "PicaAppFragment"
    val secret1 = "e6gi9vWqh225GU9KcxU9FRoNFJemspDr"
    val secret2 = "ftTEuepoipBgfCPzS6Fciu4gAJuL24gn"
    val secret3 = "pb6XkQ94iBBny1WUAxY0dY5fksexw0dt"
    val secret4 = "3Vms3EUjb7MzR6ivKkQPDOg1ldwl4XwY"
    fun categorySet(itemView: View, data: CategoryObject) {
        with(itemView) {
            var mImageSrc = ""
            var mCategoryName = ""
            mImageSrc = Tools.getThumbnailImagePath(data.thumb)
            mCategoryName = data.title
            CustomUtils.loadImage(itemView.context, mImageSrc, foo_bookImg, 0, 500)
            foo_bookName.text = mCategoryName
            //for this type,unuseless
            foo_bookName_upNews.visibility = View.GONE
            setOnClickListener {
                if (data.web == true) {
                    val stringBuilder = StringBuilder()
                    stringBuilder.append(data.link)
                    stringBuilder.append("?token=")
                    stringBuilder.append(PreferenceHelper.getToken(context.applicationContext))
                    stringBuilder.append("&secret=")
                    stringBuilder.append(secret3)
                    context.startActivity(Intent(context, BrowserView::class.java).apply {
                        putExtra(ActivityKey.KEY_CATEGORY_JUMP, stringBuilder.toString())
                    }, null)
                } else {
                    context.startActivity(Intent(context, SearchResult::class.java).apply {
                        putExtra(
                            ActivityKey.KEY_CATEGORY_JUMP,
                            Gson().toJson(com.qiuchenly.comicx.Bean.ComicCategoryBean().apply {
                                this.mCategoryName = mCategoryName
                                this.mComicType = ComicSource.BikaComic
                                this.mData = Gson().toJson(data)
                            }
                            ))
                    }, null)
                }
            }
        }
    }


    fun setWeb(id: Int) {
        var ids = id
        if (ids > 3) {
            ids = 1
            PreferenceHelper.setGirl(Comic.getContext(), false)
        } else {
            PreferenceHelper.setGirl(Comic.getContext(), true)
        }
        PreferenceHelper.setChannel(Comic.getContext(), ids)
        mViews.reInitAPI()
    }


    @SuppressLint("SetTextI18n")
    fun userProfileSet(itemView: View) {
        CustomUtils.loadImageCircle(
            itemView.context,
            "https://himg.bdimg.com/sys/portrait/item/pp.1.d2e65af8.SrGDK3snGzMAnehuVMe6mQ.jpg",
            itemView.iv_userHead
        )
        itemView.tv_userSign.setOnClickListener(null)
        itemView.lt_switchWeb.setOnClickListener { view ->
            var mdialog_view: View? = null
            if (mdialog_view == null) {
                mdialog_view = LayoutInflater.from(view.context)
                    .inflate(R.layout.dialog_switchweb, null, false)
                mdialog_view!!.rd_web1.setOnClickListener {
                    setWeb(1)
                }
                mdialog_view.rd_web2.setOnClickListener {
                    setWeb(2)
                }
                mdialog_view.rd_web3.setOnClickListener {
                    setWeb(3)
                }
            }
            when (PreferenceHelper.getChannel(Comic.getContext())) {
                1 -> {
                    mdialog_view.rd_web1.isChecked = true
                }
                2 -> {
                    mdialog_view.rd_web2.isChecked = true
                }
                3 -> {
                    mdialog_view.rd_web3.isChecked = true
                }
            }

            val dialog = AlertDialog.Builder(view.context)
                .setView(mdialog_view)
                .create()
            dialog.setCancelable(true)
            dialog.show()
        }
        if (mUser == null) {
            itemView.setOnClickListener {
                mViews.goLogin()
            }
            itemView.tv_userName.text = "点击登录"
            itemView.tv_userSign.text = "Biss"
            return
        }
        itemView.setOnClickListener(null)
        with(itemView) {
            val avatar = Tools.getThumbnailImagePath(mUser?.avatar)
            if (avatar != null && avatar != "")
                CustomUtils.loadImageCircle(itemView.context, avatar, iv_userHead)
            tv_userName.text = mUser?.name
            tv_userNick.text = mUser?.slogan
            tv_userLevel.text = "Lv.${mUser?.level}(${mUser?.exp})"
            if (mUser?.isPunched == false) {
                tv_userSign.visibility = View.VISIBLE
                tv_userSign.text = "签到"
                tv_userSign.setOnClickListener {
                    mViews.punchSign()
                }
            } else {
                tv_userSign.text = "已签到"
                tv_userSign.setOnClickListener(null)
            }
            ll_favourite.setOnClickListener(null)
            if (mFavourite != null) {
                tv_favourite.text = "" + mFavourite?.total
                ll_favourite.setOnClickListener {
                    //todo 跳转到收藏页
                }
            }

            //最近阅读:哔咔
            tv_recently.text = "" + mRecentRead
            ll_recently_read.setOnClickListener {
                val i = Intent(mContext.get(), RecentlyRead::class.java).apply {
                    putExtra(ActivityKey.KEY_RECENTLY_READ_METHOD, ComicSource.BikaComic)
                }
                mContext.get()?.startActivity(i)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return checkItemType(position)
    }

    private fun checkItemType(position: Int): Int {
        return when (position) {
            0 -> BICA_ACCOUNT
            else -> BICA_COMIC_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layout = when (viewType) {
            BICA_ACCOUNT -> layout_account
            else -> layout_category
        }
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    fun setCategory(mArr: ArrayList<CategoryObject>) {
        mItems = mArr
        notifyDataSetChanged()
    }

    private var mUser: UserProfileObject? = null
    fun setUserProfile(user: UserProfileObject) {
        mUser = user
        notifyItemChanged(0)
    }

    private var mFavourite: ComicListData? = null
    fun setFav(comics: ComicListData) {
        mFavourite = comics
        notifyItemChanged(0)
    }

    private var mRecentRead = 0
    fun setRecentRead(size: Int) {
        mRecentRead = size
        notifyItemChanged(0)
    }

    object ItemType {
        const val BICA_ACCOUNT = 0x1
        const val BICA_COMIC_TYPE = 0x2
    }
}