package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Bean.ComicInfoBean
import com.qiuchenly.comicx.Bean.LocalFavoriteBean
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.ComicDetailResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicInfoViewModel(view: ComicDetailContract.ComicInfo.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg(t.message!!)
    }

    var mView: ComicDetailContract.ComicInfo.View? = view
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
        mRealm = null
    }

    private var mRealm = Comic.getRealm()

    fun getComicInfo(bookID: String?) {
        BikaApi.getAPI()?.getComicWithId(PreferenceHelper.getToken(Comic.getContext()), bookID)
            ?.enqueue(object : Callback<GeneralResponse<ComicDetailResponse>> {
                override fun onFailure(call: Call<GeneralResponse<ComicDetailResponse>>, t: Throwable) {
                    loadFailure(Throwable("网络请求失败!"))
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicDetailResponse>>,
                    response: Response<GeneralResponse<ComicDetailResponse>>
                ) {
                    mView?.SetBikaInfo(response.body()?.data?.comic)
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