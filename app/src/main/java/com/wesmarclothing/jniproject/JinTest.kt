package com.wesmarclothing.jniproject

import android.content.Context
import com.wesmarclothing.kotlintools.kotlin.utils.d
import java.util.*

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName JinTest
 * @Date 2019/6/14 15:27
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class JinTest(context: Context) {

    //    static {
    //        System.loadLibrary("native-lib");
    //    }
    //
    //
    //    public static native String jnitest();

    private val context: Context

    init {

        this.context = context.applicationContext
    }

    fun test(){

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            JsonTest()
            Date(1990, 1, 1).time.d("当前时间")
            System.out.println("当前时间${Date(90, 0, 1, 0, 0, 0).time}")
        }

        private fun JsonTest() {

        }

        @JvmStatic
        fun main() {

        }

    }




}

