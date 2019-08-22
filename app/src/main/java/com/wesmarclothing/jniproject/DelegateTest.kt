package com.wesmarclothing.jniproject

import kotlin.properties.Delegates

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName DelegateTest
 * @Date 2019/8/14 11:10
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
object DelegateTest {


    class Person {
        var address: String by Delegates.vetoable(initialValue = "NanJing", onChange = { property, oldValue, newValue ->
            println("property: ${property.name}  oldValue: $oldValue  newValue: $newValue")
            return@vetoable newValue == "BeiJing"
        })
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val person = Person().apply { address = "NanJing" }
        person.address = "BeiJing"
        person.address = "ShangHai"
        person.address = "GuangZhou"
        println("address is ${person.address}")
    }


}