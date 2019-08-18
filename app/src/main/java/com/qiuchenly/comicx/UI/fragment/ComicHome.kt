package com.qiuchenly.comicx.UI.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHome_Category
import com.qiuchenly.comicx.Bean.HotComic
import com.qiuchenly.comicx.Bean.RecommendItemType
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.BaseImp.GridSpacingItemDecoration
import com.qiuchenly.comicx.UI.activity.MainActivity
import com.qiuchenly.comicx.UI.activity.PerferenceActivity
import com.qiuchenly.comicx.UI.adapter.ComicHomeAdapter
import com.qiuchenly.comicx.UI.view.ComicHomeContract
import com.qiuchenly.comicx.UI.viewModel.ComicHomeViewModel
import kotlinx.android.synthetic.main.fragment_my_details.*
import java.lang.ref.WeakReference

class ComicHome : BaseLazyFragment(), ComicHomeContract.View, BaseRecyclerAdapter.LoaderListener {
    override fun onGetDMZJHOT(mComicCategory: HotComic?) {
        mRecommendAdapter?.addDMZJHot(mComicCategory?.data?.data ?: ArrayList())
    }

    override fun onLoadMore(isRetry: Boolean) {
        mViewModel?.getDMZJHot()
    }

    override fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>) {
        mActivity?.hideProgress()
        mRecommendAdapter?.addDMZJData(mComicList)
    }

    override fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        mRecommendAdapter?.addDMZJCategory(mComicCategory)
        final()
    }

    override fun goSelectSource() {
        startActivity(Intent(this.context, PerferenceActivity::class.java))
    }

    override fun final() {
        if (MyDetails_Refresh.isRefreshing)
            MyDetails_Refresh.isRefreshing = false
    }

    override fun OnNetFailed(message: String?) {
        final()
        ShowErrorMsg(message!!)
        mActivity?.hideProgress()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: ComicHomeViewModel? = null
    private var mRecommendAdapter: ComicHomeAdapter? = null
    private var mActivity: MainActivity? = null
    override fun onViewFirstSelect(mPagerView: View) {
        mActivity = this.activity as MainActivity
        mViewModel = ComicHomeViewModel(this)
        mRecommendAdapter = ComicHomeAdapter(this, WeakReference(this.activity))//fix activity jump error
        MyDetails_Refresh.setOnRefreshListener {
            mActivity?.showProgress("加载推荐数据...")
            mViewModel?.getDMZJRecommend()
        }
        mRecommendAdapter?.setLoadMoreCallBack(this)
        mRecView.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendAdapter?.getSizeByItem(position) ?: 6
                }
            }
        }

        mRecView.adapter = mRecommendAdapter
        mRecView.addItemDecoration(object : GridSpacingItemDecoration() {
            override fun needFixed(position: Int): Boolean {
                return when (mRecommendAdapter?.getItemViewType(position)) {
                    RecommendItemType.TYPE.TYPE_TITLE,
                    RecommendItemType.TYPE.TYPE_TOP,
                    RecommendItemType.TYPE.TYPE_RANK
                    -> false
                    else -> true
                }
            }
        })
        mActivity?.showProgress("加载推荐数据...")
        mViewModel?.getDMZJRecommend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mRecommendAdapter = null
    }
}