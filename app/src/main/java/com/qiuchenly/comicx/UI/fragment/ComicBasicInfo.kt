package com.qiuchenly.comicx.UI.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.AppManager
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.activity.ReadPage
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.viewModel.ComicInfoViewModel
import kotlinx.android.synthetic.main.fragment_commic_basic_info.*

class ComicBasicInfo : BaseLazyFragment(), ComicDetailContract.ComicInfo.View {

    var mViewModel: ComicInfoViewModel? = null
    var mComicInfo: ComicInfoBean? = null
    private var defaultUrl = ""

    override fun getLayoutID() = R.layout.fragment_commic_basic_info

    private var lastReadPageUrl = ""
    @SuppressLint("SetTextI18n")
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicInfoViewModel(this)

        when (mComicInfo?.mComicType) {
            ComicSource.BikaComic -> {
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, ComicDetailObject::class.java)
                mViewModel?.getComicInfo(mComicInfo.comicId)
                this.mComicInfo?.mComicName = mComicInfo.title
            }
            ComicSource.DongManZhiJia -> {
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                tv_comicName.text = "${mComicInfo.title}(${mComicInfo.status})"
                this.mComicInfo?.mComicName = mComicInfo.title
                author.text = mComicInfo.sub_title
            }
            else -> {

            }
        }

        addFavorite.setOnClickListener {
            val book = mViewModel?.comicExist(mComicInfo)
            if (book == null) {
                mViewModel?.comicAdd(LocalFavoriteBean().apply {
                    this.mComicName = mComicInfo?.mComicName ?: ""
                    this.mComicImageUrl = mComicInfo?.mComicImg ?: ""
                    this.mComicData = mComicInfo?.mComicString ?: ""
                    this.mComicType = mComicInfo?.mComicType ?: ComicSource.BikaComic
                    this.mComicLastReadTime = System.currentTimeMillis()
                })
                ShowErrorMsg("已加入本地图书列表！")
                addFavorite.text = "取消收藏"
            } else {
                mViewModel?.comicDel(book.mComicName)
                ShowErrorMsg("移除成功！")
                addFavorite.text = "加入收藏"
            }
        }

        startRead.setOnClickListener {
            val bin = Intent(AppManager.appm.currentActivity(), ReadPage::class.java)
            var link = lastReadPageUrl
            ContextCompat.startActivity(AppManager.appm.currentActivity(), bin, null)
        }


        //TODO 此处需要修复以供开始阅读按钮使用
        val book = mViewModel?.comicExist(mComicInfo)
        if (book?.mComicData?.isNotEmpty() == true) {
            addFavorite.text = "取消收藏"
        }

        val point =
            null//realm.where(ComicBookInfo_Recently::class.java).equalTo("ComicName", mComicInfo?.ComicName).findFirst()
        if (point != null) {
            startRead.text = "继续阅读"
        }
    }

    override fun SetBikaInfo(comic: ComicDetailObject?) {
        tv_comicName.text = comic?.title
        author.text = comic?.author
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mComicInfo = null
        mViewModel = null
    }

    fun setDefaultIndexUrl(default: String) {
        defaultUrl = default
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
        this.mComicInfo = null
    }

    fun setUI(mComicInfo: ComicInfoBean?) {
        this.mComicInfo = mComicInfo
    }
}