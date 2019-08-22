package com.wesmarclothing.jniproject

import com.alibaba.fastjson.JSON
import java.util.*

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName SimpleSingle
 * @Date 2019/6/28 17:00
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class SimpleSingle(val name: String) : Observer {


    override fun update(o: Observable?, arg: Any?) {
//        name.d("arg：$arg")
        System.out.println("接收通知arg：$arg-----name：$name")




    }


    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {

            val observable = SimpleSingle.MyObservable()
            val simpleSingle1 = SimpleSingle(name = "我是一号")
            val simpleSingle2 = SimpleSingle(name = "我是二号")
            observable.addObserver(simpleSingle1)
            observable.addObserver(simpleSingle2)

            System.out.println("发送通知")
            observable.sendChangeMeg("我是Observable")

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
            System.out.println(parse.toString())

        }
    }


    private class MyObservable : Observable() {
        fun sendChangeMeg(content: String) {
            //方法继承自Observable，标示状态或是内容发生改变
            setChanged()
            //方法继承自Observable，通知所有观察者，最后会调用每个Observer的update方法
            notifyObservers(content)
        }
    }


}