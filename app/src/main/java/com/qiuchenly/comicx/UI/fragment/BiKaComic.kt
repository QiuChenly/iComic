package com.qiuchenly.comicx.UI.fragment

import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qiuchenly.comicx.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicx.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicx.UI.activity.AuthBica
import com.qiuchenly.comicx.UI.activity.MainActivity
import com.qiuchenly.comicx.UI.adapter.BiKaDataAdapter
import com.qiuchenly.comicx.UI.model.BicaModel
import com.qiuchenly.comicx.UI.view.BikaInterface
import kotlinx.android.synthetic.main.fragment_bika.*
import java.lang.ref.WeakReference

class BiKaComic : BaseLazyFragment(), BikaInterface {

    private var mRecycler: RecyclerView? = null
    var mRecyclerAdapter: BiKaDataAdapter? = null

    companion object {
        lateinit var model: BicaModel
    }

    private var mActivity: MainActivity? = null

    override fun onViewFirstSelect(mPagerView: View) {
        mActivity = this.activity as MainActivity
        mRecycler = view?.findViewById(R.id.rv_bika_content)
        mRecyclerAdapter = BiKaDataAdapter(this, WeakReference(activity as MainActivity))
        mRecycler?.layoutManager = GridLayoutManager(this.activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecyclerAdapter?.getSpanWithPosition(position) ?: 6
                }
            }
        }
        mRecycler?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.apply {
                    left = 10
                    right = 10
                }
            }
        })
        mRecycler?.adapter = mRecyclerAdapter


        model = ViewModelProviders.of(this).get(BicaModel::class.java)


        with(model) {
            mLint.observe(this@BiKaComic, Observer {
                ShowErrorMsg(it)
            })

            mDNS.observe(this@BiKaComic, Observer {
                initSuccess()
            })

            mUserFile.observe(this@BiKaComic, Observer {
                updateUser(it)
            })

            mFavItem.observe(this@BiKaComic, Observer {
                getFavourite(it)
            })

            mCategory.observe(this@BiKaComic, Observer {
                loadCategory(it)
            })

            mRecentSize.observe(this@BiKaComic, Observer {
                setRecentlyRead(it)
            })
        }



        swipe_bika_refresh.setOnRefreshListener {
            if (mInitBikaAPISucc) update() else reInitAPI()
        }
        reInitAPI()
    }

    override fun reInitAPI() {
        mActivity?.showProgress("初始化哔咔CDN服务器地址...")
        model.initBicaApi()
    }

    override fun setRecentlyRead(size: Int) {
        mRecyclerAdapter?.setRecentRead(size)
    }

    override fun ShowErrorMsg(msg: String) {
        super.ShowErrorMsg(msg)
        mActivity?.hideProgress()
        if (!isInitImageServer) {
            mActivity?.showProgress(true)
            //此处并不需要取消初始化，因为获取图片服务器失败也要重新获取一遍
        }
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
    }

    override fun getFavourite(comics: ComicListData) {
        mRecyclerAdapter?.setFav(comics)
    }

    override fun initImageServerSuccess() {
        if (!isInitImageServer) {
            mActivity?.showProgress(true)
            isInitImageServer = true
            mActivity?.showProgress(false, "加载哔咔类别数据...")
            update()//reinitialization application
        }
    }

    private var mInitBikaAPISucc = false
    override fun initSuccess() {
        mActivity?.hideProgress()
        update()
        mInitBikaAPISucc = true
    }

    private var isInitImageServer = false
    fun update() {
        if (model.needLogin()) {
            if (swipe_bika_refresh.isRefreshing)
                swipe_bika_refresh.isRefreshing = false
            mActivity?.hideProgress()
            return
        }
        if (!isInitImageServer) {
            mActivity?.hideProgress()
            mActivity?.showProgress(false, "正在初始化哔咔图片服务器")
            model.initImage {
                initImageServerSuccess()
            }
            return
        }
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
        mActivity?.showProgress(false, "正在加载用户信息...")
        model.updateUserInfo()
        mActivity?.showProgress(false, "正在加载漫画类别...")
        model.getCategory()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_bika
    }

    override fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?) {
        mActivity?.hideProgress()
        mRecyclerAdapter?.setCategory(mBikaCategoryArr ?: ArrayList())
    }

    override fun signResult(ret: Boolean) {
        mActivity?.hideProgress()
        if (ret) {
            ShowErrorMsg("签到成功")
            model.updateUserInfo()
        } else
            ShowErrorMsg("签到失败")
    }

    override fun punchSign() {
        mActivity?.showProgress(false, "正在签到哔咔...")
        model.punchSign {
            signResult(it)
        }
    }

    override fun updateUser(ret: UserProfileObject) {
        mActivity?.hideProgress()
        mRecyclerAdapter?.setUserProfile(ret)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 6324) {
            when (resultCode) {
                1000 -> {
                    ShowErrorMsg("登录成功:$resultCode")
                    update()
                }
                1001 -> {
                    ShowErrorMsg("我寻思你登录你吗呢?登录未成功:$resultCode")
                }
                else -> {
                    ShowErrorMsg("?")
                }
            }
            ShowErrorMsg("登录方法成功:$resultCode")
        }
    }

    override fun goLogin() {
        startActivityForResult(Intent(this.context, AuthBica::class.java), 6324)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecycler = null
        mRecyclerAdapter = null
        model.cancel()
        mActivity?.showProgress(true)
    }
}