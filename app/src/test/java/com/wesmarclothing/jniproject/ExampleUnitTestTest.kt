package com.wesmarclothing.jniproject

import org.junit.After
import org.junit.Test

import org.junit.Before
import kotlin.system.measureTimeMillis

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName ExampleUnitTestTest
 * @Date 2019/8/2 11:00
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
interface Amount {
    val value: Int
}

//inline
 class Points(override val value: Int) : Amount

class ExampleUnitTestTest {


    private var totalScore = 0L


    //测试内联类执行效率
    /**
     * 结果：使用内联类：16s
     *      不使用内联类：3s
     */
    @Test
    fun addition_isCorrect() {

        fun addToScore(amount: Amount) {
            totalScore += amount.value
        }
        measureTime {

            repeat(10_000) {
                val points = Points(it)

                repeat(1_000_000) {
                    totalScore += points.value
//                    addToScore(points)
                }
            }
        }

    }

    @Before
    fun setUp() {
    }

    @Test
    fun tearDown() {
        val timeMillis = measureTimeMillis {
            (0..10000000)
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using list way, result is : $this")
                }
        }
        println("原始方式执行时间：$timeMillis")

        val timeMillis1 = measureTimeMillis {
            (0..10000000)
                .asSequence()
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using list way, result is : $this")
                }
        }
        println("序列方式执行时间：$timeMillis1")
    }

    fun measureTime(action: () -> Unit) {
        val timeMillis1 = measureTimeMillis {
            action()
        }
        println("执行时间：$timeMillis1")
    }


    @After
    fun test1() {
    }
}