package com.wesmarclothing.jniproject.plugin

import android.app.Service
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.IBinder
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.e
import com.wesmarclothing.lib_plugin.PluginServiceInterface

class ProxyService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    private var pluginServiceInterface: PluginServiceInterface? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val className = intent?.getStringExtra("className")
        "className：$className".d()
        //反射Ac 转换成PluginInterface
        try {
            val acClass = classLoader.loadClass(className)

            val newInstance = acClass.newInstance()

            pluginServiceInterface = newInstance as PluginServiceInterface
            pluginServiceInterface?.attachService(this)

            return pluginServiceInterface?.onMyStartCommand(intent, flags, startId) ?: START_STICKY
        } catch (e: Exception) {
            e.printStackTrace()
            e.e("反射Ac")
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        pluginServiceInterface?.onMyDestroy()
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return pluginServiceInterface?.onMyUnbind(intent) ?: false
    }

    override fun onRebind(intent: Intent?) {
        pluginServiceInterface?.onMyRebind(intent)
        super.onRebind(intent)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return pluginServiceInterface?.onBind(intent)
    }

    override fun getClassLoader(): ClassLoader {
        return PluginManager.mDexClassLoader ?: super.getClassLoader()
    }

    override fun getResources(): Resources {
        return PluginManager.mResources ?: super.getResources()
    }

    override fun getAssets(): AssetManager {
        return PluginManager.mAssetManager ?: super.getAssets()
    }


}
