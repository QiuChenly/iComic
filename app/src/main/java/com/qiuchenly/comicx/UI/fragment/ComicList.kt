package com.qiuchenly.comicx.UI.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import com.qiuchenly.comicx.App
import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.adapter.ComicPageAdapter
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.viewModel.ComicListViewModel

/**
 * 漫画章节列表Fragment
 *
 */
class ComicList : BaseLazyFragment(), ComicDetailContract.Comiclist.View,
    BaseRecyclerAdapter.LoaderListener {
    override fun onLoadMore(isRetry: Boolean) {
        comicPageAdas?.setBaseID(mComicInfo!!.mComicID)
        mViewModel.getComicList(mComicInfo!!.mComicID, pageSize)
    }

    override fun loadFailure(t: Throwable) {
        comicPageAdas?.setLoadFailed()
    }

    var pageSize = 1

    override fun SetDMZJChapter(docs: ComicHomeComicChapterList) {
        if (docs.chapters.isNotEmpty())
            docs.chapters.forEach {
                comicPageAdas?.addData(getArr2Str(ArrayList(it.data)))
            }
        else {
            ShowErrorMsg("该漫画还没有上传任何章节!")
        }
    }

    override fun SetBikaPages(docs: ComicEpisodeResponse?) {
        comicPageAdas?.addData(getArr2Str(docs?.eps?.docs ?: ArrayList()))
        if (docs?.eps?.page == docs?.eps?.pages) {
            comicPageAdas?.setNoMore()
        } else {
            //自动加载所有的漫画章节
            pageSize++
            onLoadMore(false)
        }
    }

    private fun <T> getArr2Str(clazz: ArrayList<T>): ArrayList<String> {
        val mArr = ArrayList<String>()
        clazz.forEach {
            mArr.add(Gson().toJson(it))
        }
        return mArr
    }

    fun setUI(mComicInfoBean: ComicInfoBean) {
        mComicInfo = mComicInfoBean
    }

    private lateinit var mViewModel: ComicListViewModel
    private var comicPageAdas: ComicPageAdapter? = null
    private var mComicInfo: ComicInfoBean? = null
    override fun getLayoutID() = R.layout.fragment_comic_list

    @SuppressLint("SetTextI18n")
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ViewModelProviders.of(this).get(ComicListViewModel::class.java)

        mViewModel.mComicHomeList.observe(this, Observer {
            SetDMZJChapter(it)
        })

        mViewModel.mComicBicaChapterList.observe(this, Observer {
            SetBikaPages(it)
        })

        //错误提示
        mViewModel.mLintMessage.observe(this, Observer {
            Toast.makeText(Comic.getContext(), it, Toast.LENGTH_SHORT).show()
        })

        comicPageAdas = ComicPageAdapter(activity, this)
        val mListRecyclerView = mPagerView.findViewById<RecyclerView>(R.id.rv_comicPage)
        mListRecyclerView.layoutManager = LinearLayoutManager(activity)
        mListRecyclerView.adapter = comicPageAdas

        when (mComicInfo?.mComicType) {
            ComicSource.BikaComic -> {
                comicPageAdas?.setSourceType(ComicSource.BikaComic)
                mViewModel.getComicList(mComicInfo!!.mComicID, 1)
            }
            ComicSource.DongManZhiJia -> {
                comicPageAdas?.setSourceType(ComicSource.DongManZhiJia)
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                comicPageAdas?.setBaseID(mComicInfo.obj_id)
                comicPageAdas?.setNoMore()//漫画之家的漫画章节似乎是直接返回所有的.
                mViewModel.getDMZJComicList(mComicInfo.obj_id)
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        comicPageAdas?.clearContext()
        comicPageAdas = null
        mComicInfo = null
    }
}