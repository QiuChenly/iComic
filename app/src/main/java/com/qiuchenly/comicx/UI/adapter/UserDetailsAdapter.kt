package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.RecentlyRead
import com.qiuchenly.comicx.UI.view.MyDetailsContract
import com.qiuchenly.comicx.Utils.CustomUtils
import com.qiuchenly.comicx.databinding.MyMainSpecBinding
import com.qiuchenly.comicx.databinding.MyMainTopviewBinding
import io.realm.RealmResults
import java.lang.ref.WeakReference

@Suppress("ClassName", "FunctionName")
class UserDetailsAdapter(
    val mview: MyDetailsContract.View,
    private var mContext: WeakReference<Context>
) :
    BaseRecyclerAdapter<String>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            when (getItemViewType(position)) {
                TYPE_TOPVIEW -> {

                    val topUserView = MyMainTopviewBinding.bind(this)

                    topUserView.topUserName.text = "临时用户"
                    topUserView.openVIP.text = "你知道吗?有时候登录账号体验更好"
                    if (bingSrc == "") bingSrc = CustomUtils.getCachedBingUrl()
                    CustomUtils.loadImageCircle(this.context, bingSrc, topUserView.topUserImg)
                    CustomUtils.loadImage(this.context, bingSrc, topUserView.topviewBack, 20, 50)
                }
                TYPE_EXPAND_LIST -> {
                    init_SpecItem(item)
                }
                else -> {
                    //else select TYPE_NORMAL to resolve
                    val normal_item = findViewById<TextView>(R.id.normal_item)
                    val item_img = findViewById<ImageView>(R.id.item_img)
                    val recently_Size = findViewById<TextView>(R.id.recently_Size)
                    this.setOnClickListener(null)
                    recently_Size.text = "(0)"
                    normal_item.text = when (position) {
                        1 -> {
                            item_img.setImageResource(R.mipmap.local_img)
                            "本地漫画"
                        }
                        2 -> {
                            item_img.setImageResource(R.mipmap.recently_read)
                            recently_Size.text = "($mRecentlyBook)"
                            click_recently_read_item(this)
                            "最近浏览(本地)"
                        }
                        3 -> {
                            item_img.setImageResource(R.mipmap.favorite)
                            "我的收藏(本地)"
                        }
                        4 -> {
                            item_img.setImageResource(R.drawable.ic_down)
                            this.setOnClickListener {
                                /* android.support.v4.content.ContextCompat.startActivity(this.context,
                                         android.content.Intent(this.context,
                                                 com.qiuchenly.comicx.MVP.OtherTemp.DownloaderComic::class.java),
                                         null)*/
                            }
                            "下载管理"
                        }
                        else -> {
                            item_img.setImageResource(R.mipmap.other)
                            "同上"
                        }
                    } + "  "
                }
            }
        }
    }

    override fun getViewType(position: Int): Int {
        if (position > 4) return TYPE_EXPAND_LIST
        return when (position) {
            0 -> TYPE_TOPVIEW
            else -> TYPE_NORMAL
        }
    }

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            TYPE_TOPVIEW -> {
                R.layout.my_main_topview
            }
            TYPE_EXPAND_LIST -> {
                R.layout.my_main_spec
            }
            else -> {
                R.layout.my_main_normal_item
            }
        }
    }

    private var mList: ArrayList<String> = arrayListOf("", "", "", "", "", "")

    init {
        setData(mList)
    }

    private var TAG = "UserDetailsAdapter"

    private var bingSrc = ""
    fun loadImg(img: String) {
        bingSrc = img
        notifyItemChanged(0)
    }

    /**
     * 最近浏览项目
     */
    private fun click_recently_read_item(view: View) {
        view.setOnClickListener {
            val i = Intent(mContext.get(), RecentlyRead::class.java).apply {
                putExtra(ActivityKey.KEY_RECENTLY_READ_METHOD, -1)
            }
            mContext.get()?.startActivity(i)
        }
    }


    private var mFavoriteComicArr: RealmResults<LocalFavoriteBean>? = null

    @SuppressLint("SetTextI18n")
    private fun init_SpecItem(view: View) {
        with(view) {
            val mSpecViewBinding = MyMainSpecBinding.bind(this)
            setOnClickListener {
                var form = 0f
                var to = 90f
                if (mSpecViewBinding.myMainSpecList.visibility == View.GONE) mSpecViewBinding.myMainSpecList.visibility =
                    View.VISIBLE
                else {
                    mSpecViewBinding.myMainSpecList.visibility = View.GONE;form = 90f;to = 0f
                }
                mSpecViewBinding.rotateViews.startAnimation(
                    RotateAnimation(
                        form,
                        to,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f
                    ).apply {
                        duration = 200
                        fillAfter = true
                        interpolator = AccelerateInterpolator()
                    })
            }

            if (mFavoriteComicArr != null && mFavoriteComicArr!!.size > 0) {
                if (mSpecViewBinding.myMainSpecList.visibility == View.GONE) {
                    mSpecViewBinding.myMainSpecList.visibility = View.VISIBLE
                }
                mSpecViewBinding.rotateViews.startAnimation(
                    RotateAnimation(
                        0f,
                        90f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f
                    ).apply {
                        duration = 200
                        fillAfter = true
                        interpolator = AccelerateInterpolator()
                    })//设置旋转显示数据
            } else {
                mSpecViewBinding.myMainSpecList.visibility = View.GONE
            }
            mSpecViewBinding.myMainSpecList.layoutManager = LinearLayoutManager(view.context)
            if (mFavoriteComicArr != null)
                mSpecViewBinding.myMainSpecList.adapter =
                    LocalFavoriteAdapter(mFavoriteComicArr!!, mContext)
            mSpecViewBinding.myMainSpecList.isFocusableInTouchMode = false//干掉焦点冲突
            mSpecViewBinding.itemName.text = "我的收藏（本地有${mFavoriteComicArr?.size}本）"
        }
    }

    private var mRecentlyBook = 0
    fun setRecentBooks(size: Int) {
        mRecentlyBook = size
        notifyItemChanged(2)
    }

    fun setFavoriteBooks(arr: RealmResults<LocalFavoriteBean>?) {
        mFavoriteComicArr = arr
        mFavoriteComicArr?.addChangeListener { t, changeSet ->
            notifyItemRangeChanged(5, 1)
        }
    }

    fun destory() {
        mFavoriteComicArr?.removeAllChangeListeners()
    }

    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
}