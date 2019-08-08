package com.wesmarclothing.jniproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wesmarclothing.jniproject.plugin.PluginManager
import com.wesmarclothing.jniproject.plugin.ProxyActivity
import com.wesmarclothing.jniproject.plugin.ProxyService
import com.wesmarclothing.jniproject.simple.ItemListDialogFragment
import com.wesmarclothing.kotlintools.kotlin.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AspectJTestActivity : AppCompatActivity(), ItemListDialogFragment.Listener {

    override fun onItemClicked(position: Int) {
        position.d()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aspect_jtest)
        PluginManager.init(this)

        DEFAULT_LOG_TAG = "【JniActivity】"
        isDebug = true

        RxPermissionsUtils.requestReadExternalStorage(this,
            object : onRequestPermissionsListener {
                override fun onRequestBefore() {
                    toast("权限未申请")
                }

                override fun onRequestLater() {
                    toast("权限已申请")
                }
            })
    }

    fun stopService(v: View) {
        stopService(serviceIntent)
    }

    private var serviceIntent: Intent? = null

    fun startService(v: View) {
        serviceIntent = Intent(this, ProxyService::class.java)
        serviceIntent?.putExtra("className", PluginManager.mServicePath)
        startService(serviceIntent)
    }

    fun skipAc(v: View) {
        val intent = Intent(this, ProxyActivity::class.java)
        intent.putExtra("className", PluginManager.mActivityPath)
        startActivity(intent)
    }


    //加载APK文件
    fun loadApk(v: View) {
        val file = File(Environment.getExternalStorageDirectory(), "plugin.apk")
        val openRawResource = resources.openRawResource(R.raw.plugin)

        val apkPath = "android.resource://$packageName/raw/plugin.apk"
        val uri = Uri.parse(apkPath)
        PluginManager.loadApk(file.absolutePath)
    }

    fun writeBytesToFile(input: InputStream, file: File) {
        var fos: FileOutputStream? = null
        try {
            val data = ByteArray(2048)
            var nbread = -1
            fos = FileOutputStream(file)
            while (nbread > -1) {
                nbread = input.read(data)
                fos.write(data, 0, nbread)
            }
        } catch (ex: Exception) {
            ex.e()
        } finally {
            if (fos != null) {
                fos.close()
            }
        }
    }

}
