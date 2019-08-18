package com.qiuchenly.comicx.UI.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicCategoryBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.view.SearchContract
import com.qiuchenly.comicx.UI.viewModel.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseApp(), SearchContract.View {
    override fun onKeysLoadSucc(arr: ArrayList<String>) {
        mFlowLayout.adapter = object : TagAdapter<String>(arr) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(parent.context).inflate(
                    R.layout.flow_item_simple_a,
                    mFlowLayout, false
                ) as TextView
                tv.text = s
                return tv
            }
        }
        mFlowLayout.setOnTagClickListener { view, position, parent ->
            mInputEdit.setText(arr[position])
            true
        }
    }

    override fun onFailed(reasonStr: String) {

    }

    override fun getLayoutID() = R.layout.activity_search

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    private var mSearchViewModel: SearchViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchViewModel = SearchViewModel(this)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ComicSource.getAllSource())
        mSearchViewModel?.getBikaKeyWords()

        sp_search_source.adapter = adapter
        sp_search_source.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        mInputEdit.setOnEditorActionListener { v, actionId, event ->
            val mInputString = mInputEdit.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !mInputString.isNullOrEmpty()) {
                startActivity(Intent(this, SearchResult::class.java).apply {
                    val mStr = Gson().toJson(ComicCategoryBean().apply {
                        mCategoryName = "搜索关键词"
                        mData = mInputString
                    })
                    putExtra(ActivityKey.KEY_CATEGORY_JUMP, mStr)
                })
                mInputEdit.setText("")
            }
            false
        }
    }
}