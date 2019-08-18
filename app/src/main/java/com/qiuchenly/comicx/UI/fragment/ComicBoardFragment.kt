package com.qiuchenly.comicx.UI.fragment

import android.view.View
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicx.UI.BaseImp.SuperPagerAdapter
import kotlinx.android.synthetic.main.fragment_comic_board_view.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class ComicBoardFragment : BaseLazyFragment() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_comic_board_view
    }

    private var mAdapter: SuperPagerAdapter? = null

    override fun onViewFirstSelect(mPagerView: View) {
        val list = arrayListOf(
            SuperPagerAdapter.Struct("动漫之家", ComicHome()),
            SuperPagerAdapter.Struct("哔咔漫画", BiKaComic())
            //SuperPagerAdapter.Struct("以后增加", AndMore())
        )
        mAdapter = SuperPagerAdapter(this.childFragmentManager, list)
        magic_indicator_viewpager.adapter = mAdapter
        magic_indicator_viewpager.offscreenPageLimit = 1

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this.context!!, list, magic_indicator, magic_indicator_viewpager)
    }
}