package com.qiuchenly.comicx

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val mIntA = Integer(10)
        val mIntB = Integer(10)
        println(mIntA == mIntB)
        println(mIntA === mIntB)
        var a = "123"
        var b: Int = 1

        println(-b)
    }

    operator fun String.unaryPlus(): String {
        return this + this
    }
}