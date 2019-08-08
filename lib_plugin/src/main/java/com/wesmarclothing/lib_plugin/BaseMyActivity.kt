package com.wesmarclothing.lib_plugin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * @Package com.wesmarclothing.lib_targetapk
 * @FileName BaseMyActivity
 * @Date 2019/6/24 15:13
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
@SuppressLint("MissingSuperCall")
abstract class BaseMyActivity : AppCompatActivity(), PluginInterface {

    var that: Activity? = null

    override fun attachActivity(activity: Activity?) {
        that = activity
    }


    override fun startActivity(intent: Intent) {
        val newIntent = Intent()
        newIntent.putExtra("className", intent.component?.className)

        Log.d("BaseMyActivity", "Ac${that?.javaClass?.simpleName}")

        that?.startActivity(newIntent) ?: super.startActivity(intent)
    }

    override fun <T : View> findViewById(id: Int): T {
        return that?.findViewById(id) ?: super.findViewById(id)
    }

    override fun setContentView(layoutResID: Int) {
        that?.setContentView(layoutResID)
            ?: super.setContentView(layoutResID)
        Log.e("Plugin", "setContentView:that:$that")   //  AppCompatImageView
    }

    override fun getClassLoader(): ClassLoader {
        return that?.classLoader ?: super.getClassLoader()
    }

    override fun getLayoutInflater(): LayoutInflater {
        return that?.layoutInflater ?: super.getLayoutInflater()
    }

    override fun getWindow(): Window {
        return that?.window ?: super.getWindow()
    }

    override fun getWindowManager(): WindowManager {
        return that?.windowManager ?: super.getWindowManager()
    }

    override fun getResources(): Resources {
        return that?.resources ?: super.getResources()
    }

    override fun saveInstanceState(outSave: Bundle?) {}

    override fun onCreate(saveInstanceState: Bundle?) {
        that ?: super.onCreate(saveInstanceState)
        Log.e("Plugin", "onCreate:that:$that")   //  AppCompatImageView
    }


    override fun onStart() {
        that ?: super.onStart()
    }

    override fun onResume() {
        that ?: super.onResume()
    }

    override fun onRestart() {
        that ?: super.onRestart()
    }

    override fun onStop() {
        that ?: super.onStop()
    }

    override fun onPause() {
        that ?: super.onPause()
    }

    override fun onDestroy() {
        that ?: super.onDestroy()
    }

    override fun onBackPressed() {
        that ?: super.onBackPressed()
    }
}