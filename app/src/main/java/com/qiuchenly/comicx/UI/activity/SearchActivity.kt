package com.qiuchenly.comicx.UI.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicCategoryBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseApp
import com.qiuchenly.comicx.UI.view.SearchContract
import com.qiuchenly.comicx.UI.viewModel.SearchViewModel
import com.qiuchenly.comicx.databinding.ActivitySearchBinding
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter


class SearchActivity : BaseApp(), SearchContract.View {

    private lateinit var mView: ActivitySearchBinding

    override fun onKeysLoadSucc(arr: ArrayList<String>) {
        mView.flowNet.adapter = object : TagAdapter<String>(arr) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(parent.context).inflate(
                    R.layout.flow_item_simple_a,
                    mView.flowNet, false
                ) as TextView
                tv.text = s
                return tv
            }
        }

        mView.flowNet.setOnTagClickListener { view, position, parent ->
            mView.mInputEdit.setText(arr[position])
            true
        }
    }

    override fun onFailed(reasonStr: String) {

    }

    override fun getLayoutID(): View {
        // R.layout.activity_search
        mView = ActivitySearchBinding.inflate(layoutInflater)
        return mView.root
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    private var defaultType = ComicSource.BikaComic

    private var mSearchViewModel: SearchViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchViewModel = SearchViewModel(this)


        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            ComicSource.getAllSource()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
                super.getView(position, convertView, parent).apply {
                    findViewById<CheckedTextView>(android.R.id.text1).apply {
                        setTextColor(Color.WHITE)
                        //setBackgroundColor(Color.BLACK)
                    }
                }
        }
        mSearchViewModel?.getBikaKeyWords()

        mView.spSearchSource.adapter = adapter

        mView.spSearchSource.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        mView.mInputEdit.setOnEditorActionListener { v, actionId, event ->
            val mInputString = mView.mInputEdit.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && mInputString.isNotEmpty()) {
                startActivity(Intent(this, SearchResult::class.java).apply {
                    val mStr = Gson().toJson(ComicCategoryBean().apply {
                        mCategoryName = "搜索关键词"
                        mData = mInputString
                        mComicType = when (mView.spSearchSource.selectedItemPosition) {
                            0 -> ComicSource.BikaComic
                            1 -> ComicSource.DongManZhiJia
                            2 -> ComicSource.BilibiliComic
                            3 -> ComicSource.TencentComic
                            else -> ComicSource.DongManZhiJia
                        }
                    })
                    putExtra(ActivityKey.KEY_CATEGORY_JUMP, mStr)
                })
                mView.mInputEdit.setText("")
            }
            false
        }
    }
}