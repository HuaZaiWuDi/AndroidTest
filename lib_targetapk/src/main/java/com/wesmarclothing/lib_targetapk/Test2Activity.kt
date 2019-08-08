package com.wesmarclothing.lib_targetapk

import android.content.Intent
import android.os.Bundle
import com.wesmarclothing.lib_plugin.BaseMyActivity
import kotlinx.android.synthetic.main.activity_test2.*

class Test2Activity : BaseMyActivity() {

    override fun onCreated(SaveInstanceState: Bundle?) {
        super.onCreate(SaveInstanceState)
        setContentView(R.layout.activity_test2)

        skipSelf.setOnClickListener {
            startActivity(Intent(that ?: this, TestActivity::class.java))
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }
}
