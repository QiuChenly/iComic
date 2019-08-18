package com.qiuchenly.comicx.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ApplicationSetting : RealmObject() {
    @PrimaryKey
    var key: String? = null
    var mBingCachedUrl: String? = null

}