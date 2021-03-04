package com.qiuchenly.comicx.UI.BaseImp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseLazyFragment : Fragment(), BaseView {
    abstract fun getLayoutID(): View

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    private var mFirstInitialization = false
    /**
     * 父类中 #setUserVisibleHint(boolean) 优先于#onViewCreated(param1,param2)初始化,故此处无需做处理.
     * 1.( mView != null )由于第一次加载时mView可能为null,因为优先于onViewCreated方法执行.故需要加判断.
     * 2.为解决缓存左右Pager的问题,此处与onViewCreated方法内做双重判断
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mFirstInitialization = isVisibleToUser
        if (mFirstInitialization && !isInitView && mView != null) {
            isInitView = true
            onViewFirstSelect(mView!!)
        } else if (isVisibleToUser) onShowUI() else onHideUI()
    }

    fun onShowUI() {}
    fun onHideUI() {}

    private var mView: View? = null
    private var isInitView = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        if (mFirstInitialization && !isInitView) {
            isInitView = true
            onViewFirstSelect(view)
        }
    }

    abstract fun onViewFirstSelect(mPagerView: View)
    /**
     * optimization reCreate view issues
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mView ?: getLayoutID()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mView = null
    }
}