package com.wesmarclothing.jniproject.mvvm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wesmarclothing.jniproject.TestJson
import com.wesmarclothing.kotlintools.kotlin.utils.d

/**
 * @Package com.wesmarclothing.jniproject.mvvm
 * @FileName LoginViewModel
 * @Date 2019/7/18 11:45
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class LoginViewModel : ViewModel() {


    val testJson = MutableLiveData<TestJson>()


    val name = MutableLiveData<String>("我是你爸")

    val toggleGone = MutableLiveData<Boolean>(true)


    fun changedText() {
        name.value = "我是你后爸"
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    fun changedJson() {
        val json = " {\n" +
                "        \"age\": 18,\n" +
                "        \"athlDate\": \"2019-07-09T03:12:33.835Z\",\n" +
                "        \"athlDesc\": \"string\",\n" +
                "        \"avgHeart\": 1,\n" +
                "        \"avgPace\": 1,\n" +
                "        \"birthday\": \"2019-07-09T03:12:33.835Z\",\n" +
                "        \"cadence\": 1,\n" +
                "        \"calorie\": 1,\n" +
                "        \"complete\": 0.8,\n" +
                "        \"createTime\": 1511248354000,\n" +
                "        \"createUser\": 1,\n" +
                "        \"dataFlag\": 0,\n" +
                "        \"duration\": 1,\n" +
                "        \"endTime\": \"2019-07-09T03:12:33.835Z\",\n" +
                "        \"gid\": 1,\n" +
                "        \"heartCount\": 1,\n" +
                "        \"height\": 170,\n" +
                "        \"kilometers\": 1,\n" +
                "        \"maxHeart\": 1,\n" +
                "        \"maxPace\": 1,\n" +
                "        \"minHeart\": 1,\n" +
                "        \"minPace\": 1,\n" +
                "        \"sex\": 1,\n" +
                "        \"startTime\": \"2019-07-09T03:12:33.835Z\",\n" +
                "        \"status\": 101,\n" +
                "        \"stepNumber\": 1,\n" +
                "        \"updateTime\": 1511248354000,\n" +
                "        \"updateUser\": 1,\n" +
                "        \"userId\": \"string\"\n" +
                "      }"
        val parse = JSON.parseObject(json, TestJson::class.java)
        testJson.value = parse
    }


    fun itemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        position.d()
//        Toast.makeText(App.mApp, "$position", Toast.LENGTH_SHORT).show()

    }


    data class Item(val position: Int)

}