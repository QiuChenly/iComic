package com.qiuchenly.comicx.UI.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.activity.MainActivity
import com.qiuchenly.comicx.UI.adapter.UserDetailsAdapter
import com.qiuchenly.comicx.UI.model.DetailsModel
import com.qiuchenly.comicx.UI.view.MyDetailsContract
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_my_details.*
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

    private var mViewModel = DetailsModel(this)

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private fun initializationInfo() {
        mViewModel.getBingSrc()
        mViewModel.getRecentlyReadSize()
        mViewModel.getFavoriteArray()
    }

    private var TAG = "MyDetailsFragment"

    private var mUserDetailsAdapter: UserDetailsAdapter? = null
    override fun onViewFirstSelect(mPagerView: View) {
        mUserDetailsAdapter = UserDetailsAdapter(this, WeakReference(activity as MainActivity))
        mRecView.layoutManager = LinearLayoutManager(this.context)
        mRecView.adapter = mUserDetailsAdapter
        MyDetails_Refresh.setOnRefreshListener {
            initializationInfo()
            MyDetails_Refresh.isRefreshing = false
        }
        initializationInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUserDetailsAdapter?.destory()
    }
}