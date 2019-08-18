package com.qiuchenly.comicx.UI.viewModel

import com.qiuchenly.comicx.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.ComicRandomListResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import com.qiuchenly.comicx.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicx.UI.view.SearchResultView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultViewModel(view: SearchResultView) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    var mView: SearchResultView? = view

    override fun cancel() {
        super.cancel()
        mView = null
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    fun getCategoryComic_DMZJ(categoryType: String, page: Int) {
        DongManZhiJia.getV3API().getCategoryComicAll(categoryType, "0", page)
            .enqueue(object : Callback<List<ComicHome_CategoryComic>> {
                override fun onFailure(call: Call<List<ComicHome_CategoryComic>>, t: Throwable) {
                    mView?.ShowErrorMsg("加载动漫之家漫画类别时出错!")
                    mView?.getComicList_DMZJ(null)
                }

                override fun onResponse(
                    call: Call<List<ComicHome_CategoryComic>>,
                    response: Response<List<ComicHome_CategoryComic>>
                ) {
                    //当数据返回数组为空的时候表示无数据
                    mView?.getComicList_DMZJ(response.body())
                }
            })
    }

    fun getCategoryComic(categoryName: String?, page: Int) {
        BikaApi.getAPI()?.getComicList(
            PreferenceHelper.getToken(Comic.getContext()),
            page,
            categoryName,
            null,
            null,
            null,
            "ua",
            null,
            null
        )?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
            override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                mView?.ShowErrorMsg("加载漫画信息时出错!")
                mView?.getComicList_Bika(null)
            }

            override fun onResponse(
                call: Call<GeneralResponse<ComicListResponse>>,
                response: Response<GeneralResponse<ComicListResponse>>
            ) {
                mView?.getComicList_Bika(response.body()?.data?.comics)
            }
        })
    }

    fun searchComic(key: String?, page: Int) {
        BikaApi.getAPI()?.getComicListWithSearchKey(PreferenceHelper.getToken(Comic.getContext()), page, key)
            ?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
                override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                    mView?.ShowErrorMsg("搜索漫画信息时出错!")
                    mView?.getComicList_Bika(null)
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicListResponse>>,
                    response: Response<GeneralResponse<ComicListResponse>>
                ) {
                    mView?.getComicList_Bika(response.body()?.data?.comics)
                }
            })
    }

    fun getRandomComic() {
        BikaApi.getAPI()?.getRandomComicList(PreferenceHelper.getToken(Comic.getContext()))
            ?.enqueue(object : Callback<GeneralResponse<ComicRandomListResponse>> {
                override fun onFailure(call: Call<GeneralResponse<ComicRandomListResponse>>, t: Throwable) {
                    mView?.ShowErrorMsg("加载漫画信息时出错!")
                    mView?.getRandomComicList_Bika(null)
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicRandomListResponse>>,
                    response: Response<GeneralResponse<ComicRandomListResponse>>
                ) {
                    mView?.getRandomComicList_Bika(response.body()?.data?.comics)
                }
            })
    }
}