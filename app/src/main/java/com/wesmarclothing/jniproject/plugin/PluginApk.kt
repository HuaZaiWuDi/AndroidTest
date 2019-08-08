package com.wesmarclothing.jniproject.plugin

import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 * @Package com.wesmarclothing.jniproject.plugin
 * @FileName PluginApk
 * @Date 2019/6/21 11:36
 * @Author JACK
 * @Describe TODO插件apk包装类
 * @Project JNIProject
 */

class PluginApk(
    val mPackageInfo: PackageInfo,
    val mResources: Resources,
    val mAssetManager: AssetManager = mResources.assets,
    val mDexClassLoader: DexClassLoader
)