package com.qiuchenly.comicx.UI.BaseImp

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.viewpager.widget.ViewPager
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class BaseNavigatorCommon(val list: ArrayList<SuperPagerAdapter.Struct>, val vp: ViewPager) : CommonNavigatorAdapter() {

    companion object {
        fun setUpWithPager(
            context: Context,
            lists: ArrayList<SuperPagerAdapter.Struct>,
            MagicIndicator: MagicIndicator,
            ViewPage: ViewPager
        ) {
            val commonNavigator = CommonNavigator(context)
            commonNavigator.adapter = BaseNavigatorCommon(lists, ViewPage)
            commonNavigator.isAdjustMode = true//设置均分显示
            MagicIndicator.navigator = commonNavigator
            ViewPagerHelper.bind(MagicIndicator, ViewPage)//绑定
        }
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
        colorTransitionPagerTitleView.normalColor = Color.parseColor("#dfdede")//未被选择的颜色
        colorTransitionPagerTitleView.selectedColor = Color.WHITE//选择上的颜色
        colorTransitionPagerTitleView.text = list[index].name
        colorTransitionPagerTitleView.rootView.isClickable = true//打开点击效果

        //获取水波纹效果
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        val attribute = intArrayOf(android.R.attr.selectableItemBackground)
        val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        //获取结束

        colorTransitionPagerTitleView.rootView.background = typedArray.getDrawable(0)
        //设置水波纹背景

        colorTransitionPagerTitleView.setOnClickListener { vp.currentItem = index }
        //绑定Viewpager滑动回调
        return colorTransitionPagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        indicator.yOffset = UIUtil.dip2px(context, 4.0).toFloat()
        indicator.setColors(Color.parseColor("#ffffff"))
        indicator.lineHeight = UIUtil.dip2px(context, 2.0).toFloat()
        indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
        return indicator
    }
}