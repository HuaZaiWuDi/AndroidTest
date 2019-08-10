package com.wesmarclothing.ems.data.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.wesmarclothing.jniproject.BuildConfig
import com.wesmarclothing.jniproject.utils.Logger
import com.wesmarclothing.kotlintools.kotlin.utils.i
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by luyao
 * on 2018/3/13 14:58
 */
abstract class BaseRetrofitClient {

    companion object {
         const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()

            val mMessage = StringBuilder()
            //新建log拦截器
            val logging =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    val netMessage: String

                    if (message.startsWith("--> POST")) {
                        mMessage.setLength(0)
                    }
                    if (message.startsWith("{") && message.endsWith("}")
                        || message.startsWith("[") && message.endsWith("]")
                    ) {
                        netMessage = Logger.formatJson(message)
                    } else
                        netMessage = message
                    mMessage.append(netMessage + "\n")
                    if (message.startsWith("<-- END HTTP")) {
                        mMessage.i()
                    }
                })

            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addNetworkInterceptor(logging)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }
}
