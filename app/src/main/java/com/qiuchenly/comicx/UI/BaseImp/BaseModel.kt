package com.qiuchenly.comicx.UI.BaseImp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseModel : ViewModel() {
    private var mErrorMsg = MutableLiveData<String>()

    var message: LiveData<String> = mErrorMsg

    fun setError(msg: String) {
        mErrorMsg.value = msg
    }

}