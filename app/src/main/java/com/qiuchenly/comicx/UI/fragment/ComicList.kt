package com.qiuchenly.comicx.UI.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.ComicSource
import com.qiuchenly.comicx.Bean.DataItem
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.adapter.ComicPageAdapter
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import com.qiuchenly.comicx.UI.viewModel.ComicListViewModel

/**
 * 漫画章节列表获取
 *
 */
class ComicList : BaseLazyFragment(), ComicDetailContract.Comiclist.View, BaseRecyclerAdapter.LoaderListener {
    override fun onLoadMore(isRetry: Boolean) {
        mViewModel?.getComicList(mComicInfo!!.mComicID, pageSize)
    }

    override fun loadFailure(t: Throwable) {
        comicPageAdas?.setLoadFailed()
    }

    var pageSize = 1

    override fun showMsg(str: String) {

    }

    override fun SetDMZJChapter(docs: ComicHomeComicChapterList) {
        if (docs.chapters.isNotEmpty())
            docs.chapters.forEach {
                comicPageAdas?.addData(getArr2Str(ArrayList(it.data)))
            }
        else {
            ShowErrorMsg("该漫画还没有上传任何章节!")
        }
    }

    override fun SetBikaPages(docs: ComicEpisodeResponse?, id: String) {
        comicPageAdas?.setBaseID(id)
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

    var mViewModel: ComicListViewModel? = null
    private var comicPageAdas: ComicPageAdapter? = null
    private var mComicInfo: ComicInfoBean? = null
    override fun getLayoutID() = R.layout.fragment_comic_list

    fun scrollWithPosition(position: Int) {
        /* if (mListRecyclerView != null) {
             mListRecyclerView!!.scrollToPosition(position)
             val manager = mListRecyclerView!!.layoutManager as LinearLayoutManager
             manager.scrollToPositionWithOffset(position, 0)
         }*/
    }

    @SuppressLint("SetTextI18n")
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicListViewModel(this)
        comicPageAdas = ComicPageAdapter(activity, this)
        val mListRecyclerView = mPagerView.findViewById<RecyclerView>(R.id.rv_comicPage)
        mListRecyclerView.layoutManager = LinearLayoutManager(activity)
        mListRecyclerView.adapter = comicPageAdas

        when (mComicInfo?.mComicType) {
            ComicSource.BikaComic -> {
                comicPageAdas?.setSourceType(ComicSource.BikaComic)
                mViewModel?.getComicList(mComicInfo!!.mComicID, 1)
            }
            ComicSource.DongManZhiJia -> {
                comicPageAdas?.setSourceType(ComicSource.DongManZhiJia)
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                comicPageAdas?.setBaseID(mComicInfo.obj_id)
                comicPageAdas?.setNoMore()//漫画之家的漫画章节似乎是直接返回所有的.
                mViewModel?.getDMZJComicList(mComicInfo.obj_id)
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        comicPageAdas?.clearContext()
        comicPageAdas = null
        mViewModel?.cancel()
        mViewModel = null
        mComicInfo = null
    }
}