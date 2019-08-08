package com.wesmarclothing.jniproject

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by Administrator on 2017/3/10.
 */

object RxPermissionsUtils {

    //请求Camera权限
    fun requestCamera(mContext: Context, onRequestPermissionsListener: onRequestPermissionsListener?) {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(mContext as Activity, arrayOf(Manifest.permission.CAMERA), 1)
            onRequestPermissionsListener?.onRequestBefore()
        } else {
            onRequestPermissionsListener?.onRequestLater()
        }
    }

    fun requestCall(mContext: Context, onRequestPermissionsListener: onRequestPermissionsListener?) {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(mContext as Activity, arrayOf(Manifest.permission.CALL_PHONE), 1)
            onRequestPermissionsListener?.onRequestBefore()
        } else {
            onRequestPermissionsListener?.onRequestLater()
        }
    }


    fun requestWriteExternalStorage(mContext: Context, onRequestPermissionsListener: onRequestPermissionsListener?) {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
            onRequestPermissionsListener?.onRequestBefore()
        } else {
            onRequestPermissionsListener?.onRequestLater()
        }
    }

    fun requestReadExternalStorage(mContext: Context, onRequestPermissionsListener: onRequestPermissionsListener?) {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
            onRequestPermissionsListener?.onRequestBefore()
        } else {
            onRequestPermissionsListener?.onRequestLater()
        }
    }

    fun requestLoaction(mContext: Context, onRequestPermissionsListener: onRequestPermissionsListener?) {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            onRequestPermissionsListener?.onRequestBefore()
        } else {
            onRequestPermissionsListener?.onRequestLater()
        }
    }

    /**
     * 自定义权限
     *
     * @param mContext
     * @param permission
     * @param onRequestPermissionsListener
     */
    fun requestPermission(
        mContext: Activity,
        permission: String,
        onRequestPermissionsListener:onRequestPermissionsListener
    ) {
        if (ContextCompat.checkSelfPermission(mContext, permission) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.READ_SMS, permission), 1)
            onRequestPermissionsListener.onRequestBefore()
        } else {
            onRequestPermissionsListener.onRequestLater()
        }
    }
}
