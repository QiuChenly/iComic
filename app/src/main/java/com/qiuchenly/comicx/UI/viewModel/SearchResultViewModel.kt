package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qiuchenly.comicx.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicx.Core.Comic
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicx.ProductModules.Bika.responses.ComicRandomListResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.ComicHome.DongManZhiJia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultViewModel : ViewModel() {

    private var errorMsg = MutableLiveData<String>()
    var Msg: LiveData<String> = errorMsg

    private var mGetDMZJCategory = MutableLiveData<List<ComicHome_CategoryComic>>()
    var mDMZJCategory: LiveData<List<ComicHome_CategoryComic>> = mGetDMZJCategory

    private var mGetBicaCategory = MutableLiveData<ComicListData>()
    var mBicaCategory: LiveData<ComicListData> = mGetBicaCategory

    fun getCategoryComic_DMZJ(categoryType: String, page: Int) {
        DongManZhiJia.getV3API().getCategoryComicAll(categoryType, "0", page)
            .enqueue(object : Callback<List<ComicHome_CategoryComic>> {
                override fun onFailure(call: Call<List<ComicHome_CategoryComic>>, t: Throwable) {
//                    mView?.getComicList_DMZJ(null)
                    errorMsg.value = "加载动漫之家漫画类别时出错!"
                }

                override fun onResponse(
                    call: Call<List<ComicHome_CategoryComic>>,
                    response: Response<List<ComicHome_CategoryComic>>
                ) {
                    //当数据返回数组为空的时候表示无数据
                    mGetDMZJCategory.value = response.body()
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
                errorMsg.value = "加载漫画信息时出错!" + t.localizedMessage
            }

            override fun onResponse(
                call: Call<GeneralResponse<ComicListResponse>>,
                response: Response<GeneralResponse<ComicListResponse>>
            ) {
                mGetBicaCategory.value = response.body()?.data?.comics
            }
        })
    }

    fun searchComic(key: String?, page: Int) {
        BikaApi.getAPI()
            ?.getComicListWithSearchKey(PreferenceHelper.getToken(Comic.getContext()), page, key)
            ?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<ComicListResponse>>,
                    t: Throwable
                ) {
                    errorMsg.value = "搜索漫画信息时出错!"
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicListResponse>>,
                    response: Response<GeneralResponse<ComicListResponse>>
                ) {
                    mGetBicaCategory.value = response.body()?.data?.comics
                }
            })
    }

    fun getRandomComic(comics: (ArrayList<ComicListObject>) -> Unit) {
        BikaApi.getAPI()?.getRandomComicList(PreferenceHelper.getToken(Comic.getContext()))
            ?.enqueue(object : Callback<GeneralResponse<ComicRandomListResponse>> {
                override fun onFailure(
                    call: Call<GeneralResponse<ComicRandomListResponse>>,
                    t: Throwable
                ) {
                    errorMsg.value = "加载漫画信息时出错!"
                }

                override fun onResponse(
                    call: Call<GeneralResponse<ComicRandomListResponse>>,
                    response: Response<GeneralResponse<ComicRandomListResponse>>
                ) {
                    comics(response.body()?.data?.comics ?: return)
                }
            })
    }

    fun searchComic_DongManZhiJia(searchKey: String, page: Int) {
        DongManZhiJia.getV3API().getSearchResult(searchKey, page, System.currentTimeMillis())
            .enqueue(object : Callback<ArrayList<ComicHome_CategoryComic>> {
                override fun onFailure(
                    call: Call<ArrayList<ComicHome_CategoryComic>>,
                    t: Throwable
                ) {
                    errorMsg.value = "搜索动漫之家漫画时出错!"
                }

                override fun onResponse(
                    call: Call<ArrayList<ComicHome_CategoryComic>>,
                    response: Response<ArrayList<ComicHome_CategoryComic>>
                ) {
                    //当数据返回数组为空的时候表示无数据
                    mGetDMZJCategory.value = response.body()?.toList() ?: return
                }
            })
    }
}