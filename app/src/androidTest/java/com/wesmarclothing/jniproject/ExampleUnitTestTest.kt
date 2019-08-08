package com.wesmarclothing.jniproject

import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test


/**
 * @Package com.wesmarclothing.jniproject
 * @FileName ExampleUnitTestTest
 * @Date 2019/8/2 11:00
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTestTest {

    @Rule
    var mTestRule = ActivityTestRule<MainActivity>()

    @Test
    fun test(){
        mTestRule

    }

}