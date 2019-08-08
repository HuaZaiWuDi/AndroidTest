package com.wesmarclothing.jniproject.plugin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import com.wesmarclothing.kotlintools.kotlin.d
import com.wesmarclothing.kotlintools.kotlin.e
import dalvik.system.DexClassLoader

/**
 * @Package com.wesmarclothing.jniproject.plugin
 * @FileName PluginManager
 * @Date 2019/6/21 11:36
 * @Author JACK
 * @Describe TODO 手写插件化，实现APK文件的加载及跳转，但是报资源未找到错误
 *
 * @Project JNIProject
 */

@SuppressLint("StaticFieldLeak")
object PluginManager {


    lateinit var context: Context
     var mDexClassLoader: DexClassLoader?=null
     var mResources: Resources?=null
     var mAssetManager: AssetManager?=null
    var mActivityPath: String = "com.wesmarclothing.lib_targetapk.TestActivity"
    var mServicePath: String = "com.wesmarclothing.lib_targetapk.MyService"


    fun init(context: Context) {
        this.context = context.applicationContext
    }

    //加载APK文件
    fun loadApk(apkPath: String) {
        "文件路径$apkPath".d()
        try {
            //缓存文件路径
            val dexOutFile = context.getDir("dex", Context.MODE_PRIVATE)

            mDexClassLoader = DexClassLoader(apkPath, dexOutFile.absolutePath, null, context.classLoader)

            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageArchiveInfo(
                apkPath,
                PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
            )
            packageInfo.activities?.forEach {
                "Ac：$it".d()
            }
            mActivityPath = packageInfo.activities?.lastOrNull()?.name ?: ""
            "mActivityPath：$mActivityPath".d()

            mServicePath = packageInfo.services?.lastOrNull()?.name ?: ""

            packageInfo.services?.forEach {
                "Service：$it".d()
            }

            //实例化AssetManager
            mAssetManager = AssetManager::class.java.newInstance()
            val addAssetPath = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(mAssetManager, apkPath)

            //实例化Resources
            mResources = Resources(mAssetManager, context.resources.displayMetrics, context.resources.configuration)

            "mResources：$mResources".d()
            "assetManager：$mAssetManager".d()

        } catch (e: Exception) {
            e.e("loadApk")
        }
    }
}