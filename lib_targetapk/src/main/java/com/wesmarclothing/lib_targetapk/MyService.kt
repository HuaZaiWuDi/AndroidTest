package com.wesmarclothing.lib_targetapk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.wesmarclothing.lib_plugin.BaseService

class MyService : BaseService() {
    override fun onMyUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onMyStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("TAG", "onMyStartCommand" + flags)
        return Service.START_STICKY
    }

    override fun onMyDestroy() {
        super.onMyDestroy()
        Log.e("TAG", "onMyDestroy")
    }

}
