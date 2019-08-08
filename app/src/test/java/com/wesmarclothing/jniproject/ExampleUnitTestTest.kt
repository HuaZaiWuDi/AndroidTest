package com.wesmarclothing.jniproject

import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName ExampleUnitTestTest
 * @Date 2019/8/2 11:00
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class ExampleUnitTestTest {

    @Test
    fun addition_isCorrect() {
        println(1111)
    }

    @Before
    fun setUp() {
        println(2222)
    }

    @Test
    fun tearDown() {
        println(333)
    }


    @After
    fun test1() {
        println(4444)
    }
}