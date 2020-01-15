package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.ComicDetailResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicInfoViewModel : BaseModel() {

    private var bicaComicInfo = MutableLiveData<ComicDetailObject>()
    var mBicaComic: LiveData<ComicDetailObject> = bicaComicInfo

    fun cancel() {
        mRealm = null
    }

    private var mRealm = Comic.getRealm()

    fun getComicInfo(bookID: String?) {
        BikaApi.getAPI()?.getComicWithId(PreferenceHelper.getToken(Comic.getContext()), bookID)
            ?.enqueue(object : Callback<GeneralResponse<ComicDetailResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<ComicDetailResponse>>,
                    t: Throwable
                ) {
                    setError("网络请求失败!")
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicDetailResponse>>,
                    response: Response<GeneralResponse<ComicDetailResponse>>
                ) {
                    bicaComicInfo.value = response.body()?.data?.comic ?: return
                }
            })
    }

    fun comicExist(mComicInfo: ComicInfoBean?): LocalFavoriteBean? {
        return mRealm?.where(LocalFavoriteBean::class.java)
            ?.equalTo("mComicName", mComicInfo?.mComicName)
            ?.findFirst()
    }

    fun comicDel(book: String) {
        val data = mRealm?.where(LocalFavoriteBean::class.java)
            ?.equalTo("mComicName", book)
            ?.findFirst()
        mRealm?.beginTransaction()
        data?.deleteFromRealm()
        mRealm?.commitTransaction()
    }

    fun comicAdd(book: LocalFavoriteBean) {
        mRealm?.executeTransaction {
            it.copyToRealm(book)
        }
    }
}