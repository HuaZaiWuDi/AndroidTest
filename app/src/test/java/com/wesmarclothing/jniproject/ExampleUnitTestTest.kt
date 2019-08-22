package com.wesmarclothing.jniproject

import org.junit.After
import org.junit.Before
import org.junit.Test
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


    @Test
    fun scope() {
        var i = 0
        /**
         * 运行代码块，返回最后一行的结果
         */
        val run1 = run {
            i
        }

        /**
         * 扩展函数，传递函数为this
         */
        val run = i.run {
            "i:$this"
        }


        /**
         * 传递一个参数，返回最后一行的结果
         */
        val str = with(i) {
            "i:$i"
        }


        /**
         * 返回最后一行的结果
         */
        val let = let {
            "i:$i"
        }

        /**
         * 扩展函数，传递函数为it,
         */
        val let1 = i.let {
            "i:$it this$this"
        }

        /**
         * 传递it,返回i对象，类似builder模式。并且只传递原始值
         */
        i.also {
            i += 5  //6
        }.also {
            i -= 10 //-4
        }.also {
            i   //10
        }

        "".isNotBlank()


        val stringList: MutableList<String> = mutableListOf("a", "b", "c", "d")
        val intList: List<Int> = mutableListOf(1, 2, 3, 4)
        printList(stringList)//这里实际上是编译不通过的
        printList(intList)//这


    }

    fun printList(list: List<Any>) {
//        list.add(3.0f)//开始引入危险操作dangerous! dangerous! dangerous!
        list.forEach {
            println(it)
        }
    }


    @Test
    fun boolean() {
        val intList: List<Int> = mutableListOf(1, 2, 3, 4)
        intList
            .filter {
                it == 2
            }
            .contains(3)
            .yes {
                "正确"
            }
            .no {
                "不正确"
            }
            .println()


        true
            .yes {
                0
            }
            .no {
                0.1f
            }
            .println()



        if (true) {
            "yes".println()
        } else {
            "no".println()
        }

    }
}





fun Any.println() {
    println(this)
}

sealed class BooleanExt<out T>//定义成协变

object Otherwise : BooleanExt<Nothing>()//Nothing是所有类型的子类型，协变的类继承关系和泛型参数类型继承关系一致

class TransferData<T>(val data: T) : BooleanExt<T>()//data只涉及到了只读的操作


//声明成inline函数
inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when {
    this -> {
        TransferData(block.invoke())
    }
    else -> Otherwise
}

inline fun <T> BooleanExt<T>.no(block: () -> T): T = when (this) {
    is Otherwise ->
        block()
    is TransferData ->
        this.data
}

