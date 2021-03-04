package com.qiuchenly.comicx.UI.adapter

import android.content.Intent
import android.view.View
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.PerferenceActivity
import com.qiuchenly.comicx.UI.viewModel.MainActivityViewModel
import com.qiuchenly.comicx.databinding.ItemFunctionBinding

class FunctionAdapter : BaseRecyclerAdapter<MainActivityViewModel.FunctionType>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_function
    }

    override fun onViewShow(item: View, data: MainActivityViewModel.FunctionType, position: Int, ViewType: Int) {
        with(item) {
            val itemFunction = ItemFunctionBinding.bind(this)
            itemFunction.functionName.text = data.title
            if (position == itemCount - 1) itemFunction.mSplitLine.visibility = View.INVISIBLE

            when (data.functionType) {
                MainActivityViewModel.FunctionType.Types.SETTING -> {
                    setOnClickListener {
                        item.context.startActivity(Intent(this.context, PerferenceActivity::class.java))
                    }
                }
                else -> {

                }
            }
        }
    }
}