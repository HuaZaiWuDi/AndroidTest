package com.wesmarclothing.jniproject.test

import org.jetbrains.annotations.TestOnly

/**
 * @Package com.wesmarclothing.jniproject.test
 * @FileName Caluater
 * @Date 2019/8/2 9:52
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class Caluater {


    fun add(a: Int, b: Int): Int {
        return a + b
    }

}


class CaluterTest {

    val caluater=Caluater()


    @TestOnly
    fun test(){

    }


}
