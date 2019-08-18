package com.qiuchenly.comicx.UI.BaseImp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BaseFragmentPagerStatement(fm: FragmentManager, private val _fragment: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return _fragment[position]
    }

    override fun getCount(): Int {
        return _fragment.size
    }
}
