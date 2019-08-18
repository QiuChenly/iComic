package com.qiuchenly.comicx.UI.view

import com.qiuchenly.comicx.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicx.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicx.UI.BaseImp.BaseView
import java.util.*

interface BikaInterface : BaseView {
    fun updateUser(ret: UserProfileObject)
    fun punchSign()
    fun signResult(ret: Boolean)
    fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?)
    fun initImageServerSuccess()
    fun initSuccess()
    fun getFavourite(comics: ComicListData)
    fun reInitAPI()
    fun setRecentlyRead(size: Int)
}