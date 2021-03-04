package com.qiuchenly.comicx.UI.fragment

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.qiuchenly.comicx.Bean.ComicComm
import com.qiuchenly.comicx.Bean.ComicHomeCategory
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
import com.qiuchenly.comicx.ViewCreator.RefreshView
import com.qiuchenly.comicx.databinding.FragmentMyDetailsBinding
import java.lang.ref.WeakReference

class ComicHome : BaseLazyFragment(), ComicHomeContract.View, BaseRecyclerAdapter.LoaderListener {
    override fun onGetDMZJHOT(mComicCategory: HotComic?) {
        mRecommendAdapter?.addDMZJHot(mComicCategory?.data?.data ?: ArrayList())
    }

    override fun onLoadMore(isRetry: Boolean) {
        mViewModel.getDMZJHot(54)
        mViewModel.getDMZJHot(52)
        mViewModel.getDMZJHot(50)
    }

    override fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>) {
        //mActivity?.hideProgress()
        mRecommendAdapter?.addDMZJData(mComicList)
    }

    override fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHomeCategory>) {
        mRecommendAdapter?.addDMZJCategory(mComicCategory)
        final()
    }

    override fun goSelectSource() {
        startActivity(Intent(this.context, PerferenceActivity::class.java))
    }

    override fun final() {
        mFragmentMyDetailsBinding.updates.stopRefreshing(false)
//        if (MyDetails_Refresh.isRefreshing)
//            MyDetails_Refresh.isRefreshing = false
    }

    override fun OnNetFailed(message: String?) {
        final()
        ShowErrorMsg(message!!)
        //mActivity?.hideProgress()
    }

    private lateinit var mFragmentMyDetailsBinding: FragmentMyDetailsBinding
    override fun getLayoutID(): View {
//        return R.layout.fragment_my_details
        mFragmentMyDetailsBinding = FragmentMyDetailsBinding.inflate(layoutInflater)
        return mFragmentMyDetailsBinding.root
    }

    private lateinit var mViewModel: ComicHomeViewModel
    private var mRecommendAdapter: ComicHomeAdapter? = null
    private var mActivity: MainActivity? = null
    override fun onViewFirstSelect(mPagerView: View) {
        mActivity = this.activity as MainActivity
        mViewModel = ViewModelProvider(this).get(ComicHomeViewModel::class.java)

        with(mViewModel) {
            message.observe(this@ComicHome, Observer {
                OnNetFailed(it)
            })

            mCategoryBean.observe(this@ComicHome, Observer {
                onGetDMZJCategory(it)
            })

            mHotComicBean.observe(this@ComicHome, Observer {
                onGetDMZJHOT(it)
            })

            mmRecommendBean.observe(this@ComicHome, Observer {
                onGetDMZRecommendSuch(it)
            })
        }

        mRecommendAdapter =
            ComicHomeAdapter(this, WeakReference(this.activity))//fix activity jump error
//        MyDetails_Refresh.setOnRefreshListener {
//            mActivity?.showProgress("加载推荐数据...")
//            mViewModel.getDMZJRecommend()
//        }
        mFragmentMyDetailsBinding.updates.setTintColor(Color.BLACK)
        mFragmentMyDetailsBinding.updates.setBackgroundImage(R.mipmap.beijing)
        mFragmentMyDetailsBinding.updates.setUpdate(object : RefreshView.callback {
            override fun onRefresh() {
                //mActivity?.showProgress("加载推荐数据...")
                mViewModel.getDMZJRecommend()
            }

            override fun onLoadMore() {

            }
        })
        mRecommendAdapter?.setLoadMoreCallBack(this)
        mFragmentMyDetailsBinding.mRecView.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendAdapter?.getSizeByItem(position) ?: 6
                }
            }
        }

        mFragmentMyDetailsBinding.mRecView.adapter = mRecommendAdapter
        mFragmentMyDetailsBinding.mRecView.addItemDecoration(object : GridSpacingItemDecoration() {
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
        //mActivity?.showProgress("加载推荐数据...")
        mViewModel.getDMZJRecommend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecommendAdapter = null
    }
}