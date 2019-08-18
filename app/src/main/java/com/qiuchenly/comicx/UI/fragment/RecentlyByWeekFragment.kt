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
import kotlinx.android.synthetic.main.recently_week.*
import java.lang.ref.WeakReference

class RecentlyByWeekFragment : BaseLazyFragment(), WeekContract.View {

    override fun getLayoutID(): Int {
        return R.layout.recently_week
    }

    private var mMyDetailsLocalBookList: MyRecentlyAdapter? = null
    private var mPres = RecentlyModel(this)
    private var mSource = -1
    override fun onViewFirstSelect(mPagerView: View) {
        val intent = activity?.intent
        if (intent != null) {
            mSource = intent.extras?.getInt(ActivityKey.KEY_RECENTLY_READ_METHOD) ?: -1
        }
        rv_recently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyAdapter(WeakReference(activity as RecentlyRead))

        RecentlyModel(this)

        val arr: ArrayList<RecentlyReadingBean> = when (mSource) {
            -1 -> mPres.getAllRecently()
            else -> mPres.getTargetRecently(mSource)
        }
        mMyDetailsLocalBookList!!.setData(arr)
//        mMyDetailsLocalBookList!!.sort(1)
        rv_recently.adapter = mMyDetailsLocalBookList
    }

    fun reInitData() {
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
    }
}