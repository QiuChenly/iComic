package com.qiuchenly.comicx.Utils

abstract class BannerThread : Thread() {
    //立刻同步到子线程中
    var flag = true

    fun setStart() {
        flag = true
        start()
    }

    fun setStop() {
        flag = false
    }

    abstract fun runAble()

    override fun run() {
        //立刻同步到子线程中
        while (flag) {
            sleep(10000)
            runAble()
        }
        println("线程彻底退出了")
    }
}