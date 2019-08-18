package com.qiuchenly.comicx.UI.BaseImp

interface BaseLoadingView {
    /**
     *  要不是DataBinding这个龙鸣技术有大bug 我才不写这种龙鸣代码!!SB Google 出来挨打
     */
    fun onLoading()

    fun onLoadSuccess()
    fun onLoadFailed()
}