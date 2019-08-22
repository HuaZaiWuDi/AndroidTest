package com.wesmarclothing.jniproject

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.github.moduth.blockcanary.BlockCanary
import com.kongzue.dialog.v2.DialogSettings
import com.wesmarclothing.jniproject.blockcanary.AppBlockCanaryContext
import com.wesmarclothing.kotlintools.kotlin.utils.isDebug
import java.util.*

/**
 * @Package com.wesmarclothing.jniproject
 * @FileName App
 * @Date 2019/7/12 18:32
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class App : MultiDexApplication() {

    var store: Stack<Activity>? = null

    companion object {
        var mApp: App? = null
    }


    override fun onCreate() {
        super.onCreate()
        isDebug = true
        DialogSettings.use_blur = false
        mApp = this
        store = Stack()
        initActivityCallback()
        BlockCanary.install(this, AppBlockCanaryContext()).start()

    }

    private fun initActivityCallback() {
        val value = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                store?.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                store?.add(activity)
            }
        }
        registerActivityLifecycleCallbacks(value)
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    fun getCurActivity(): Activity? {
        return store?.lastElement()
    }


    override fun onLowMemory() {
        super.onLowMemory()

    }
}