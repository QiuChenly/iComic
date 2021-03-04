package com.qiuchenly.comicx.UI.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.activity.MainActivity
import com.qiuchenly.comicx.UI.adapter.UserDetailsAdapter
import com.qiuchenly.comicx.UI.model.DetailsModel
import com.qiuchenly.comicx.UI.view.MyDetailsContract
import com.qiuchenly.comicx.ViewCreator.RefreshView
import com.qiuchenly.comicx.databinding.FragmentMyDetailsBinding
import io.realm.RealmResults
import java.lang.ref.WeakReference

class MyDetailsFragment : BaseLazyFragment(), MyDetailsContract.View {
    override fun setLocateComic(realmResults: RealmResults<LocalFavoriteBean>?) {
        mUserDetailsAdapter?.setFavoriteBooks(realmResults)
    }

    override fun setRecentlySize(size: Int) {
        mUserDetailsAdapter?.setRecentBooks(size)
    }

    override fun onSrcReady(img: String) {
        mUserDetailsAdapter?.loadImg(img)
    }

    private lateinit var mViewModel: DetailsModel //= DetailsModel(this)

    private lateinit var mFragmentMyDetailsBinding: FragmentMyDetailsBinding
    override fun getLayoutID(): View {
//        return R.layout.fragment_my_details
        mFragmentMyDetailsBinding = FragmentMyDetailsBinding.inflate(layoutInflater)
        return mFragmentMyDetailsBinding.root
    }

    private fun initializationInfo() {
        mViewModel.getBingSrc()
        mViewModel.getRecentlyReadSize()
        mViewModel.getFavoriteArray()
        mFragmentMyDetailsBinding.updates.stopRefreshing(false)
    }

    private var TAG = "MyDetailsFragment"

    private var mUserDetailsAdapter: UserDetailsAdapter? = null
    override fun onViewFirstSelect(mPagerView: View) {

        mViewModel = ViewModelProviders.of(this).get(
            DetailsModel::class.java
        )

        with(mViewModel) {
            message.observe(this@MyDetailsFragment, Observer {
                ShowErrorMsg(it)
            })

            mBingImage.observe(this@MyDetailsFragment, Observer {
                onSrcReady(it)
            })

            mRecentSize.observe(this@MyDetailsFragment, Observer {
                setRecentlySize(it)
            })


            mLocalFavoriteComic.observe(this@MyDetailsFragment, Observer {
                setLocateComic(it)
            })
        }

        mUserDetailsAdapter = UserDetailsAdapter(this, WeakReference(activity as MainActivity))
        mFragmentMyDetailsBinding.mRecView.layoutManager = LinearLayoutManager(this.context)
        mFragmentMyDetailsBinding.mRecView.adapter = mUserDetailsAdapter
//        MyDetails_Refresh.setOnRefreshListener {
//            initializationInfo()
//            MyDetails_Refresh.isRefreshing = false
//        }
        mFragmentMyDetailsBinding.updates.setUpdate(object : RefreshView.callback {
            override fun onLoadMore() {

            }

            override fun onRefresh() {
                initializationInfo()
            }
        })
        initializationInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUserDetailsAdapter?.destory()
    }
}