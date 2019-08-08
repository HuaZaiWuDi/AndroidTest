package com.wesmarclothing.lib_plugin

import android.app.Service
import android.content.Intent
import android.content.res.Resources

/**
 * @Package com.wesmarclothing.lib_plugin
 * @FileName BaseService
 * @Date 2019/6/29 10:14
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
abstract class BaseService : Service(), PluginServiceInterface {

    var that: Service? = null

    override fun attachService(mService: Service) {
        that = mService
    }

    override fun onMyCreate() {
        that?.onCreate()
    }

    override fun getClassLoader(): ClassLoader {
        return that?.classLoader ?: super.getClassLoader()
    }

    override fun getResources(): Resources {
        return that?.resources ?: super.getResources()
    }

//    override fun onMyStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (that == null)
//            return super.onStartCommand(intent, flags, startId)
//        return START_STICKY
//    }

    override fun onMyDestroy() {
        that ?: super.onDestroy()
    }


//    override fun onMyUnbind(intent: Intent?): Boolean {
//        if (that == null)
//            return super.onUnbind(intent)
//        return false
//    }

    override fun onMyRebind(intent: Intent?) {
        that ?: super.onRebind(intent)
    }

}