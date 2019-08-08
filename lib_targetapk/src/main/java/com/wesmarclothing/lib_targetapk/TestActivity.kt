package com.wesmarclothing.lib_targetapk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.wesmarclothing.lib_plugin.BaseMyActivity

/**
 * @Package com.wesmarclothing.lib_targetapk
 * @FileName TestActivity
 * @Date 2019/6/24 10:09
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class TestActivity : BaseMyActivity() {


    override fun onCreated(SaveInstanceState: Bundle?) {
//        startActivity(Intent(that ?: this, Test2Activity::class.java))
        super.onCreate(SaveInstanceState)
        setContentView(R.layout.activity_test)

        Toast.makeText(that ?: this, "点击了", Toast.LENGTH_SHORT).show()

        val id = findViewById<TextView>(R.id.tv_click)

        id.setOnClickListener {
            Toast.makeText(that ?: this, "点击了", Toast.LENGTH_SHORT).show()

            startActivity(Intent(that ?: this, Test2Activity::class.java))
        }

        Log.e("Plugin", "" + id)   //  AppCompatImageView
    }

}