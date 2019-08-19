package com.qiuchenly.comicx.UI.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicCategoryBean
import com.qiuchenly.comicx.Bean.ComicHomeCategory
import com.qiuchenly.comicx.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.adapter.SearchResultAdapter
import com.qiuchenly.comicx.UI.view.SearchResultView
import com.qiuchenly.comicx.UI.viewModel.SearchResultViewModel
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class SearchResult : BaseApp(), SearchResultView, BaseRecyclerAdapter.LoaderListener {
    @SuppressLint("SetTextI18n")
    override fun getComicList_DMZJ(list: List<ComicHome_CategoryComic>?) {
        hideProgress()
        if (list != null) {
            if (list.isEmpty()) {
                mAdapter?.setNoMore()
                return
            }
            //修复数据显示问题
            nextPage++
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "加载结束 (当前第 $nextPage 页)"
            mAdapter?.addDMZJComic(list)
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun getRandomComicList_Bika(data: ArrayList<ComicListObject>?) {
        hideProgress()
        if (data != null) {
            //修复数据显示问题
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "搜索结果 (随机本子,每次加载二十个,无限加载.)"
            mAdapter?.addBikaComic(data)
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun onLoadMore(isRetry: Boolean) {
        activityName_secondTitle.text = "搜索结果 (正在加载下一页...)"
        selectLoad()
    }

    override fun showMsg(str: String) {
        ShowErrorMsg(str)
    }

    @SuppressLint("SetTextI18n")
    override fun getComicList_Bika(data: ComicListData?) {
        hideProgress()
        if (data != null) {
            //修复数据显示问题
            val page = if (data.page > data.pages) data.pages else data.page
            activityName.text =
                mCategory.mCategoryName + if (mCategory.mCategoryName == "搜索关键词") " - " + mCategory.mData else ""
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "搜索结果 (共找到${data.total}部,当前第$page/${data.pages}页)"
            if (nextPage > data.pages) {
                mAdapter?.setNoMore()
            } else {
                nextPage = data.page + 1
                mAdapter?.addBikaComic(data.docs)
            }
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun getLayoutID() = R.layout.activity_search_result
    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    fun selectLoad() {
        when (mCategory.mComicType) {
            ComicSource.DongManZhiJia -> {
                mViewModel?.getCategoryComic_DMZJ(mCategoryID, nextPage)
            }
            ComicSource.BikaComic -> {
                when (mCategory.mCategoryName) {
                    "随机本子" -> {
                        mViewModel?.getRandomComic()
                    }
                    "最近更新" -> {
                        mViewModel?.getCategoryComic(null, nextPage)
                    }
                    "搜索关键词" -> {
                        mViewModel?.searchComic(mCategory.mData, nextPage)
                    }
                    else -> {
                        mViewModel?.getCategoryComic(mCategory.mCategoryName, nextPage)
                    }
                }
            }
        }
    }

    var mViewModel: SearchResultViewModel? = SearchResultViewModel(this)
    var mAdapter: SearchResultAdapter? = null
    var nextPage = 1
    var mCategoryID = ""
    lateinit var mCategoryObj: CategoryObject
    lateinit var mCategory: ComicCategoryBean
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val str = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        if (str.isNullOrEmpty()) {
            ShowErrorMsg("数据错误,可能是我没做这个功能或者正在开发中.")
            finish()
            return
        }

        back_up.setOnClickListener {
            finish()
        }

        mResultList.layoutManager = LinearLayoutManager(this)
        mResultList.itemAnimator = DefaultItemAnimator()
        mResultList.addItemDecoration(object : RecyclerView.ItemDecoration() {
        })
        mAdapter = SearchResultAdapter(this)
        mResultList.adapter = mAdapter

        mCategory = Gson().fromJson(str, ComicCategoryBean::class.java)
        if (mCategory.mCategoryName != "搜索关键词")
            mCategoryObj = Gson().fromJson(mCategory.mData, CategoryObject::class.java)
        magic_indicator.visibility = View.GONE

        showProgress("加载漫画结果中...")
        when (mCategory.mComicType) {
            ComicSource.BikaComic -> {
                handle_bika(mCategory)
            }
            ComicSource.DongManZhiJia -> {
                handle_ComicHome(mCategory)
            }
        }
        activityName.text = mCategory.mCategoryName
    }

    private fun handle_ComicHome(mComicCategoryBean: ComicCategoryBean) {
        val id = Gson().fromJson(mComicCategoryBean.mData, ComicHomeCategory::class.java)
        mCategoryID = id.tag_id
        nextPage = 0
        selectLoad()
    }

    private fun handle_bika(mComicCategoryBean: ComicCategoryBean) {
        selectLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
    }
}