package com.wesmarclothing.ems.data.net

import android.net.ParseException
import android.util.Log
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Package com.wesmarclothing.ems.net
 * @FileName HandleNetExceptionManager
 * @Date 2019/7/18 11:55
 * @Author JACK
 * @Describe TODO
 * @Project EMS-Kotlin
 */
object HandleNetExceptionManager {


    fun handleResponseError(t: Throwable): String {
        Log.e("网络异常：", t.toString())
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        var msg = "请求网络失败"
        if (t is UnknownHostException) {
            msg = "网络不可用"
        } else if (t is SocketTimeoutException) {
            msg = "网络连接超时"
        } else if (t is HttpException) {

            Log.e("网络异常：", convertStatusCode(t))
            msg = "网络异常"
        } else if (t is JsonParseException
            || t is ParseException
            || t is JSONException
            || t is JsonIOException
            || t is JsonSyntaxException
            || t is com.alibaba.fastjson.JSONException
        ) {
            Log.e("网络数据异常：", t.toString())
            msg = "网络数据异常"
        } else if (t is ConnectException) {
            msg = "请求网络失败"
        } else {

        }
        return msg
    }

    private fun convertStatusCode(httpException: HttpException): String {
        val msg: String
        if (httpException.code() == 500) {
            msg = "服务器异常"
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在"
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝"
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面"
        } else {
            msg = httpException.message()
        }
        return msg
    }


}