package com.wesmarclothing.jniproject

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//@RunWith(Parameterized::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.wesmarclothing.jniproject", appContext.packageName)
    }

    @Test
    fun add() {
        System.out.println("测试开始")
        assertEquals(4, 2)
        System.out.println("测试结束")
    }


//    @Parameterized.Parameters
//    fun data(): Collection<Int> {
//        return arrayListOf(1, 2, 3, 4, 5)
//    }

    @Parameterized.Parameters
    fun data(): Collection<Int> {
        return ArrayList(Arrays.asList(1, 2, 3, 4))
    }

//    @Parameterized.Parameters
//    fun prime(): Collection<Int> {
//        return arrayListOf(1, 2, 3, 4, 5)
//    }


//    @Test(expected = ParseException::class)
//    fun testData() {
//        assertEquals(4, index)
//    }

    @Test
    fun main(args: Array<String>) {
        println(111)
    }


}
