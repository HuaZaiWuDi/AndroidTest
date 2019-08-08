package com.wesmarclothing.lib_plugin

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @Package com.wesmarclothing.lib_plugin
 * @FileName PluginServiceInterface
 * @Date 2019/6/29 10:02
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
interface PluginServiceInterface {


    fun attachService(mService: Service)

    fun onMyCreate()

    fun onMyStartCommand(intent: Intent?, flags: Int, startId: Int): Int

    fun onMyDestroy()


    fun onMyUnbind(intent: Intent?): Boolean

    fun onMyRebind(intent: Intent?)


    fun onBind(intent: Intent?): IBinder?

}