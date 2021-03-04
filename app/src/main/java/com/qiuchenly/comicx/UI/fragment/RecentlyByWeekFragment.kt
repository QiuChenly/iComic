package com.qiuchenly.comicx.UI.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.activity.RecentlyRead
import com.qiuchenly.comicx.UI.adapter.MyRecentlyAdapter
import com.qiuchenly.comicx.UI.model.RecentlyModel
import com.qiuchenly.comicx.UI.view.WeekContract
import com.qiuchenly.comicx.databinding.RecentlyWeekBinding
import java.lang.ref.WeakReference

class RecentlyByWeekFragment : BaseLazyFragment(), WeekContract.View {

    private lateinit var mRecentlyWeekBinding: RecentlyWeekBinding
    override fun getLayoutID(): View {
//        return R.layout.recently_week
        mRecentlyWeekBinding = RecentlyWeekBinding.inflate(layoutInflater)
        return mRecentlyWeekBinding.root
    }

    private var mMyDetailsLocalBookList: MyRecentlyAdapter? = null
    private var mPres = RecentlyModel()
    private var mSource = -1
    override fun onViewFirstSelect(mPagerView: View) {
        val intent = activity?.intent
        if (intent != null) {
            mSource = intent.extras?.getInt(ActivityKey.KEY_RECENTLY_READ_METHOD) ?: -1
        }
        mRecentlyWeekBinding.rvRecently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyAdapter(WeakReference(activity as RecentlyRead))

        val arr: ArrayList<RecentlyReadingBean> = when (mSource) {
            -1 -> mPres.getAllRecently()
            else -> mPres.getTargetRecently(mSource)
        }
        mMyDetailsLocalBookList!!.setData(arr)
//        mMyDetailsLocalBookList!!.sort(1)
        mRecentlyWeekBinding.rvRecently.adapter = mMyDetailsLocalBookList
    }

    fun reInitData() {
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
    }
}