package com.wesmarclothing.jniproject

import org.junit.Test
import kotlin.properties.Delegates

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName DelegateTest
 * @Date 2019/8/14 11:10
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class DelegateTest {


    class Person {
        //属性只要操作就能监听的到
        var address: String by Delegates.vetoable(initialValue = "NanJing", onChange = { property, oldValue, newValue ->
            println("property: ${property.name}  oldValue: $oldValue  newValue: $newValue")
            //只有当返回的结果为True时才允许修改
            return@vetoable newValue == "BeiJing"
        })
    }


    class Person2 {
        //属性只要操作就能监听的到
        var address: String by Delegates.observable(
            initialValue = "NanJing",
            onChange = { property, oldValue, newValue ->
                println("property: ${property.name}  oldValue: $oldValue  newValue: $newValue")
            })
    }

    class Person3 {
        //属性使用的时候再初始化，并且必须在操作之前进行初始化，
        //优点，可以监听基本数据类型
        //
        var address: String by Delegates.notNull()
    }


    @Test
    fun main() {
        println("Delegates.vetoable")
        val person = Person().apply { address = "NanJing" }
        person.address = "BeiJing"
        person.address = "ShangHai"
        person.address = "GuangZhou"
        println("address is ${person.address}")



        println("Delegates.observable")
        val person2 = Person2().apply { address = "ShangHai" }
        person2.address = "BeiJing"
        person2.address = "ShenZhen"
        person2.address = "GuangZhou"
        println("address is ${person2.address}")


        println("Delegates.notNull")
        val person3 = Person3().apply { address = "ShangHai" }
        person3.address = "BeiJing"
        person3.address = "ShenZhen"
        person3.address = "GuangZhou"
        println("address is ${person3.address}")
    }


    @Test
    fun preferenceDelegate() {

    }
}