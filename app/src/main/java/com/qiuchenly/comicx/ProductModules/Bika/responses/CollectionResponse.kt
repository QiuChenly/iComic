package com.qiuchenly.comicx.ProductModules.Bika.responses

import com.qiuchenly.comicx.ProductModules.Bika.ComicListObject

data class CollectionsResponse(val arrayList: ArrayList<CollectionObject>) {
    data class CollectionObject(val comics: ArrayList<ComicListObject>, val title: String)
}