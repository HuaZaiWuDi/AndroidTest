package com.wesmarclothing.jniproject

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName `CoroutineTest、`
 * @Date 2019/7/10 15:28
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
object CoroutineTest {

    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0

    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        massiveRun(counterContext) {
            // 在单线程的上下文中运行每个协程
            counter++
        }
        println("Counter = $counter")
    }

    suspend fun massiveRun(context: CoroutineContext, action: suspend () -> Unit) {
        val n = 1000 // 启动协程的数量
        val k = 1000 // 每个协程执行动作的次数
        val time = measureTimeMillis {
            val jobs = List<Job>(n) {
                GlobalScope.launch(context) {
                    repeat(k) { action }
                }
            }
            jobs.forEach { it.join() }
        }
        println("Completed ${n * k} actions in $time ms")
    }


}