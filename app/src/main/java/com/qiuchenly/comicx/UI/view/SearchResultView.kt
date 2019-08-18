package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.UI.BaseImp.BaseView

interface SearchResultView : BaseView {
    fun getComicList_Bika(data: ComicListData?)
    fun getRandomComicList_Bika(data: ArrayList<ComicListObject>?)
    fun getComicList_DMZJ(list: List<ComicHome_CategoryComic>?)
}