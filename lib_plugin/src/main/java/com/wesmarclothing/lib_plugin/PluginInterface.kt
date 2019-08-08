package com.wesmarclothing.lib_plugin

import android.app.Activity
import android.os.Bundle

/**
 * @Package com.wesmarclothing.lib_plugin
 * @FileName PluginInterface
 * @Date 2019/6/24 9:46
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
public interface PluginInterface {

    fun onCreated(SaveInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onRestart()

    fun onStop()

    fun onPause()

    fun onDestroy()

    fun saveInstanceState(outSave: Bundle?)

    fun onBackPressed()

    fun attachActivity(activity: Activity?)


}