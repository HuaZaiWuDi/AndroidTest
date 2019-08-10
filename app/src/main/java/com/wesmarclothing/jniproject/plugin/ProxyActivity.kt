package com.wesmarclothing.jniproject.plugin

import android.app.Activity
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.e
import com.wesmarclothing.lib_plugin.PluginInterface


/**
 * 这个不能使用AppCompatActivity(),会报一个主题未找到异常
 * **/
class ProxyActivity : Activity() {
    private var pluginInterface: PluginInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val className = intent.getStringExtra("className")
        "className：$className".d()
        //反射Ac 转换成PluginInterface
        try {
            val acClass = classLoader.loadClass(className)

            val newInstance = acClass.newInstance()

            pluginInterface = newInstance as PluginInterface

            pluginInterface?.attachActivity(this)
            val bundle = Bundle()
            pluginInterface?.onCreated(bundle)
        } catch (e: Exception) {
            e.printStackTrace()
            e.e("反射Ac")
        }
    }

    override fun onStart() {
        super.onStart()
        pluginInterface?.onStart()
    }

    override fun onResume() {
        super.onResume()
        pluginInterface?.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        pluginInterface?.onRestart()
    }

    override fun onPause() {
        super.onPause()
        pluginInterface?.onPause()
    }

    override fun onStop() {
        super.onStop()
        pluginInterface?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginInterface?.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pluginInterface?.onBackPressed()
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


//    override fun getTheme(): Resources.Theme {
//        return PluginManager.mResources.newTheme() ?: super.getTheme()
//    }

    override fun startActivity(intent: Intent?) {
        val className = intent?.getStringExtra("className")
        val intent1 = Intent(this, ProxyActivity::class.java)
        intent1.putExtra("className", className)
        super.startActivity(intent1)
    }

}
