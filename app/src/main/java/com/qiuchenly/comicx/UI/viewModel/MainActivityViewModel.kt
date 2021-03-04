package com.qiuchenly.comicx.UI.viewModel

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.qiuchenly.comicx.App
import com.qiuchenly.comicx.Bean.ComicCategoryBean
import com.qiuchenly.comicx.Bean.RandomImageBean
import com.qiuchenly.comicx.Bean.TempInfo
import com.qiuchenly.comicx.Bean.WeatherBean
import com.qiuchenly.comicx.Core.ActivityKey
import com.qiuchenly.comicx.HttpRequests.WeatherRequest
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Common.BaseURL
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseFragmentPagerStatement
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import com.qiuchenly.comicx.UI.activity.MainActivity
import com.qiuchenly.comicx.UI.activity.SearchActivity
import com.qiuchenly.comicx.UI.activity.SearchResult
import com.qiuchenly.comicx.UI.adapter.FunctionAdapter
import com.qiuchenly.comicx.UI.fragment.ComicCloudFragment
import com.qiuchenly.comicx.UI.fragment.MyDetailsFragment
import com.qiuchenly.comicx.UI.view.MainActivityCallback
import com.qiuchenly.comicx.Utils.CustomUtils
import com.qiuchenly.comicx.databinding.ActivitySwitchMainBinding
import com.qiuchenly.comicx.databinding.NavigationMainBinding
import com.yalantis.jellytoolbar.listener.JellyListener
import com.yalantis.jellytoolbar.widget.JellyToolbar
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

/**
 * 主界面的ViewModel层
 */
class MainActivityViewModel :
    MainActivityCallback.Callbacks,
    BaseModel() {

    private lateinit var mActivitySwitchMainBinding: ActivitySwitchMainBinding
    private lateinit var mNavigationMainBinding: NavigationMainBinding

    fun setActivity(mContentView: MainActivity) {
        this.mContentView = mContentView

        mActivitySwitchMainBinding = mContentView.getThisViewBing()

        mNavigationMainBinding = NavigationMainBinding.bind(mActivitySwitchMainBinding.inDl.root)

        with(mContentView) {
            mSwitchList.add(mActivitySwitchMainBinding.switchMyListImg)
            mSwitchList.add(mActivitySwitchMainBinding.switchMyWebsiteMoreImg)
            mSwitchList.add(mActivitySwitchMainBinding.switchMyWebsiteAdditionImg)

            mActivitySwitchMainBinding.switchMyListImg.imageAlpha = 255
            mActivitySwitchMainBinding.switchMyWebsiteMoreImg.imageAlpha = 100
            mActivitySwitchMainBinding.switchMyWebsiteAdditionImg.imageAlpha = 100
            mActivitySwitchMainBinding.dlNavigationMain.addDrawerListener(mCallback)
            val statement = BaseFragmentPagerStatement(
                supportFragmentManager,
                mFragments
            )
            mActivitySwitchMainBinding.vpMainPages.adapter = statement
            mActivitySwitchMainBinding.vpMainPages.offscreenPageLimit = 1
            mActivitySwitchMainBinding.vpMainPages.addOnPageChangeListener(mCallback)

            mNavigationMainBinding.closedApp.setOnClickListener {
                App.closedApp()
            }
            mActivitySwitchMainBinding.btnMenuMain.setOnClickListener {
                mActivitySwitchMainBinding.dlNavigationMain.openDrawer(GravityCompat.START)
                isOpenDrawable = true
            }
            mActivitySwitchMainBinding.switchMyList.tag = 0
            mActivitySwitchMainBinding.switchMyList.setOnClickListener(this)
            mActivitySwitchMainBinding.switchMyWebsiteMore.tag = 1
            mActivitySwitchMainBinding.switchMyWebsiteMore.setOnClickListener(this)
            mActivitySwitchMainBinding.switchMyWebsiteAddition.tag = 2
            mActivitySwitchMainBinding.switchMyWebsiteAddition.setOnClickListener(this)

            mFuncAdapter = FunctionAdapter()
            mFuncAdapter?.setData(getFunctionList())
            mNavigationMainBinding.mFunMenu.layoutManager = LinearLayoutManager(this)
            mNavigationMainBinding.mFunMenu.adapter = mFuncAdapter

            mActivitySwitchMainBinding.btnMenuSearch.setOnClickListener {
                this.runOnUiThread {
                    startActivity(Intent(this, SearchActivity::class.java))
                }
            }


            mToolbar = WeakReference(mActivitySwitchMainBinding.toolbar)
            val linearLayoutSearch =
                LayoutInflater.from(this).inflate(
                    R.layout.toolbar_search_bar,
                    null
                ) as LinearLayoutCompat
            val editText =
                linearLayoutSearch.findViewById<AppCompatEditText>(R.id.et_searchContent)
            val advanceButton = linearLayoutSearch.findViewById<Button>(R.id.search_advanceSearch)

            advanceButton.setOnClickListener {
                mToolbar?.get()?.jellyListener?.onCancelIconClicked()
                startActivity(Intent(this, SearchActivity::class.java))
            }

            editText.isFocusable = false
            editText.isFocusableInTouchMode = false
            editText.setOnEditorActionListener { v, actionId, event ->
                val mInputString = editText.text.toString()
                if (actionId == EditorInfo.IME_ACTION_SEARCH && mInputString.isNotEmpty()) {
                    startActivity(Intent(this, SearchResult::class.java).apply {
                        val mStr = Gson().toJson(ComicCategoryBean().apply {
                            mCategoryName = "搜索关键词"
                            mData = mInputString
                        })
                        putExtra(ActivityKey.KEY_CATEGORY_JUMP, mStr)
                    })
                    editText.setText("")
                    mToolbar?.get()?.jellyListener?.onCancelIconClicked()
                }
                false
            }

            mToolbar?.get()?.jellyListener = object : JellyListener() {
                override fun onToolbarExpandingStarted() {
                    super.onToolbarExpandingStarted()
                    mActivitySwitchMainBinding.controlMenu.visibility = View.INVISIBLE
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(editText, 0)
                    toolbarIsOpen = true
                }

                override fun onCancelIconClicked() {
                    mToolbar?.get()?.collapse()
                    mActivitySwitchMainBinding.controlMenu.visibility = View.VISIBLE
                    editText.isFocusable = false
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                    toolbarIsOpen = false
                }
            }
            mToolbar?.get()?.contentView = linearLayoutSearch
        }
    }

    private lateinit var mContentView: MainActivity
    private var TAG = "MainActivityViewModel"

    fun loadFailure() {
        mContentView.ShowErrorMsg("天气信息加载失败!")
        val errInfo = "网络异常."
        with(mContentView) {
            mNavigationMainBinding.mDateInfo.text = "请下拉刷新"
            mNavigationMainBinding.mDateStatus.text = errInfo
            mNavigationMainBinding.mDateTemp.text = "?"
            mNavigationMainBinding.mDatePM.text = errInfo
            CustomUtils.loadImage(
                this,
                "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp",
                mNavigationMainBinding.mWeatherImg,
                30,
                500
            )
            mNavigationMainBinding.mUpdateInfo.isRefreshing = false
        }
    }

    fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        val retStr = response.body()?.string()
        if (retStr == null || retStr.indexOf("mh-date-wraper") == -1) loadFailure()
        else thread {
            val js = Jsoup.parse(retStr)
            val mNode = js.getElementsByClass("mh-date-wraper")
            val mTempInfo = ArrayList<TempInfo>()
            mNode[0].getElementsByClass("t-cont").forEachIndexed { i, element ->
                var mElement = element.childNode(0)
                val time = mElement.childNode(0)
                val mRealTime = (time as Element).text()
                mElement = (element.childNode(1).childNode(1))
                //3°
                val temps = (mElement.childNode(0) as Element).text()

                val status = (mElement.childNode(1).childNode(0) as Element).text()
                //<p class="mh-desc-3"><span>多云转晴</span><span>持续无风向微风</span><span>-3~7℃</span></p>

                val PM_2_5 = (mElement.childNode(1).childNode(1) as Element).text()
                //<p class="mh-desc-4"><span>PM2.5值：</span><span class = "mh-pm25 mh-pm25-level-4">172&nbsp;中度 < / span > < / p >
                mTempInfo.add(TempInfo().apply {
                    this.mRealTime = mRealTime
                    this.PM_2_5 = PM_2_5
                    this.status = status
                    this.temps = temps
                })
            }

            mContentView.runOnUiThread {
                with(mContentView) {
                    mNavigationMainBinding.mDateInfo.text = mTempInfo[0].mRealTime
                    mNavigationMainBinding.mDateStatus.text = mTempInfo[0].status
                    mNavigationMainBinding.mDateTemp.text = mTempInfo[0].temps
                    mNavigationMainBinding.mDatePM.text = mTempInfo[0].PM_2_5
                    CustomUtils.loadImage(
                        this,
                        "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp",
                        mNavigationMainBinding.mWeatherImg,
                        30,
                        500
                    )
                    var imgUrl = BaseURL.WEATHER_DUO_YUN
                    with(mTempInfo[0].status) {
                        if (indexOf("多云") != -1) {
                        }
                        if (indexOf("多云转小雨") != -1) {
                            imgUrl = BaseURL.WEATHER_RAIN
                        }
                        if (indexOf("小雨转多云") != -1) {
                            imgUrl = BaseURL.WEATHER_RAIN
                        }
                        if (indexOf("中雨") != -1) {
                            imgUrl = BaseURL.WEATHER_MIDDLE_RAIN
                        }
                        if (indexOf("阴") != -1) {
                            imgUrl = BaseURL.WEATHER_YING
                        }
                    }
                    CustomUtils.loadImage(this, imgUrl, mNavigationMainBinding.mWeatherImgRight, 0, 200)

                    mNavigationMainBinding.mUpdateInfo.isRefreshing = false
                }
            }
        }
    }

    private var mSwitchList = ArrayList<ImageView>()
    /**
     * 上一个点击的pager序号
     */
    private var mCurrentPosition = 0

    /**
     * Main页面的所有碎片化容器聚合
     */
    private val mFragments = ArrayList<Fragment>().apply {
        add(MyDetailsFragment())
        add(ComicCloudFragment())
        //TODO will add thirty page
        //add(Main())
    }

    private var mCallback: MainActivityCallback = MainActivityCallback(this)

    /**
     * 侧滑菜单是否开启
     */
    private var isOpenDrawable = false

    /**
     * 用于确定按下按键时间是否小于2秒
     */
    private var lastTime = -1L

    private var mCall: Call<ResponseBody>? = null

    private var mFuncAdapter: FunctionAdapter? = null

    private var mToolbar: WeakReference<JellyToolbar>? = null

    private var toolbarIsOpen = false

    private fun getFunctionList(): ArrayList<FunctionType> {
        val arrs = ArrayList<FunctionType>()
        arrs.add(FunctionType().apply {
            title = "漫画主页"
            functionType = FunctionType.Types.MAIN
        })
        arrs.add(FunctionType().apply {
            title = "软件设置"
            functionType = FunctionType.Types.SETTING
        })
        return arrs
    }

    class FunctionType {

        enum class Types {
            MAIN, SETTING
        }

        var title = ""
        var functionType = Types.MAIN
    }


    override fun PagerSelect(position: Int) {
        mSwitchList[mCurrentPosition].imageAlpha = 100
        mSwitchList[position].imageAlpha = 255
        mCurrentPosition = position
    }

    override fun DrawerChange(state: Int) {
        isOpenDrawable = state == MainActivityCallback.TYPE_OPEND
    }

    private fun closeDrawer() {
        mActivitySwitchMainBinding.dlNavigationMain.closeDrawer(GravityCompat.START)
    }

    fun canExit(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK || event?.keyCode == KeyEvent.KEYCODE_ENTER) return false
        return if (isOpenDrawable && keyCode == KeyEvent.KEYCODE_BACK) {
            closeDrawer()
            false
        } else if (toolbarIsOpen || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            when (event?.keyCode) {
                KeyEvent.KEYCODE_ENTER,
                KeyEvent.KEYCODE_BACK ->
                    mToolbar?.get()?.jellyListener?.onCancelIconClicked()
            }
            false
        } else {
            val curr = System.currentTimeMillis()
            if (curr - lastTime > 2000) {
                mContentView.ShowErrorMsg("再按一次退出")
                lastTime = curr
                false
            } else {
                App.closedApp()
                true
            }
        }
    }

    fun cancel() {
        if (mCall != null) mCall?.cancel()
    }

    fun getWeathers() {
        getWeathersV2()
        /*mCall = BikaApi
            .getCusUrl(BaseUrl = BaseURL.BASE_WEATHER)
            .create(WeatherRequest::class.java)
            .getWeatherInfo()
        mCall?.enqueue(this)*/
    }

    private var mWeather = MutableLiveData<WeatherBean.Data>()

    var mWeatherData: LiveData<WeatherBean.Data> = mWeather

    /**
     * 获取天气接口v2
     */
    private fun getWeathersV2() {
        val mCall = BikaApi
            .getCusUrl(BaseUrl = BaseURL.BASE_WEATHER_V2, isJsonBody = true)
            .create(WeatherRequest::class.java)
            .getWeatherByTV()
        mCall.enqueue(object : Callback<WeatherBean> {
            override fun onFailure(call: Call<WeatherBean>, t: Throwable) {
                setError("获取天气信息失败!")
            }

            override fun onResponse(call: Call<WeatherBean>, response: Response<WeatherBean>) {
                mWeather.value = response.body()?.data ?: return
            }
        })
    }

    private var mRandomImage = MutableLiveData<RandomImageBean>()

    var randomImage: LiveData<RandomImageBean> = mRandomImage

    fun getRandomImage() {
        val mCall = BikaApi
            .getCusUrl(BaseUrl = BaseURL.BASE_RAMDOM_IMAGE, isJsonBody = true)
            .create(WeatherRequest::class.java)
            .getRandomBackgroung()
        mCall.enqueue(object : Callback<RandomImageBean> {
            override fun onFailure(call: Call<RandomImageBean>, t: Throwable) {
                setError("获取随机背景图信息失败!")
            }

            override fun onResponse(
                call: Call<RandomImageBean>,
                response: Response<RandomImageBean>
            ) {
                val it = response.body() ?: return
                if (it.data.isEmpty()) {
                    setError("获取导航栏背景失败!请检查网络或关闭VPN.")
                    getRandomImage()
                    return
                }
                mRandomImage.value = it
            }
        })
    }
}