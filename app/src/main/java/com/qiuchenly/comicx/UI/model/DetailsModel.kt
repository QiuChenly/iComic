package com.qiuchenly.comicx.UI.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Bean.RecentlyReadingBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.HttpRequests.BingRequests
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Common.BaseURL
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import com.qiuchenly.comicx.Utils.CustomUtils
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class DetailsModel : BaseModel() {

    private var mBingImageSrc = MutableLiveData<String>()
    var mBingImage: LiveData<String> = mBingImageSrc

    private var mRecentlySize = MutableLiveData<Int>()
    var mRecentSize: LiveData<Int> = mRecentlySize

    private var mLocalFavoriteArray = MutableLiveData<RealmResults<LocalFavoriteBean>>()
    var mLocalFavoriteComic: LiveData<RealmResults<LocalFavoriteBean>> = mLocalFavoriteArray

    private var mRealm: WeakReference<Realm?>? = null

    init {
        mRealm = WeakReference(Comic.getRealm())
    }

    fun cancel() {
        mRealm = null
        if (mCall?.isCanceled == false) {
            mCall?.cancel()
            mCall = null
        }
        mRecent?.removeAllChangeListeners()
        mRecent = null
    }

    fun getBingSrc() {
        mCall = BikaApi.getCusUrl(BaseUrl = BaseURL.BASE_URL_BING)
            .create(BingRequests::class.java)
            .getImageSrc()
        mCall!!.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                setError("访问bing服务器失败!")
                mBingImageSrc.value = CustomUtils.getCachedBingUrl()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var str = response.body()?.string() ?: return
                str = CustomUtils.subStr(str, "rel=\"preload\" href=\"", "&amp;")
                if (str.indexOf(BaseURL.BASE_URL_BING) == -1)
                    str = BaseURL.BASE_URL_BING + str
                if (str != CustomUtils.getCachedBingUrl()) {
                    CustomUtils.setCachedBingUrl(str)
                }
                mBingImageSrc.value = str
            }
        })
    }

    private var mRecent: RealmResults<RecentlyReadingBean>? = null
    fun getRecentlyReadSize() {
        mRecent = mRecent ?: mRealm?.get()?.where(RecentlyReadingBean::class.java)?.findAll()
        mRecent?.addChangeListener { t, Set ->
            mRecentlySize.value = mRecent?.size ?: 0
        }
        mRecentlySize.value = mRecent?.size ?: 0
    }

    fun getFavoriteArray() {
        mLocalFavoriteArray.value = mRealm?.get()?.where(LocalFavoriteBean::class.java)
            ?.sort("mComicLastReadTime", Sort.DESCENDING)
            ?.findAll() ?: return
    }

    private var mCall: Call<ResponseBody>? = null

}