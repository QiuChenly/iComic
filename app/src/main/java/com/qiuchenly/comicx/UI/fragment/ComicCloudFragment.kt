package com.qiuchenly.comicx.UI.fragment

import android.view.View
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicx.UI.BaseImp.SuperPagerAdapter
import com.qiuchenly.comicx.databinding.FragmentComicBoardViewBinding

class ComicCloudFragment : BaseLazyFragment() {

    private lateinit var mFragmentComicBoardView: FragmentComicBoardViewBinding
    override fun getLayoutID(): View {
//        return R.layout.fragment_comic_board_view
        mFragmentComicBoardView = FragmentComicBoardViewBinding.inflate(layoutInflater)
        return mFragmentComicBoardView.root
    }

    private var mAdapter: SuperPagerAdapter? = null

    override fun onViewFirstSelect(mPagerView: View) {
        val list = arrayListOf(
            SuperPagerAdapter.Struct("动漫之家", ComicHome()),
            SuperPagerAdapter.Struct("哔咔漫画", BiKaComic())
            //SuperPagerAdapter.Struct("以后增加", AndMore())
        )
        mAdapter = SuperPagerAdapter(this.childFragmentManager, list)
        mFragmentComicBoardView.magicIndicatorViewpager.adapter = mAdapter
        mFragmentComicBoardView.magicIndicatorViewpager.offscreenPageLimit = 1

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(
            this.requireContext(),
            list,
            mFragmentComicBoardView.miMagicIndicator.magicIndicator,
            mFragmentComicBoardView.magicIndicatorViewpager
        )
    }
}