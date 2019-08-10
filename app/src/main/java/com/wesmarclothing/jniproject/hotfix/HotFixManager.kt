package com.wesmarclothing.jniproject.hotfix

import android.content.Context
import com.wesmarclothing.jniproject.utils.FileUtils
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.e
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Array

/**
 * @Package com.wesmarclothing.jniproject.hotfix
 * @FileName HotFixManager
 * @Date 2019/7/12 16:44
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */

/**
 * Tinker 热修复步骤
 * 1、创建BaseDexClassLoader 子类 DexClassLoader
 * 2、用DexClassLoader 加载我们已经修复好的Dex文件
 * 3、要将修复的Dex文件和App的Dex文件合并，生成DexElements数据
 * 4、将生成的Dex文件设置优先级最高，排在最前面，索引为0
 * 5、通过反射技术，将最新的DexElements复制到系统的PathList中
 */

object ArrayUtil {

    fun combineArray(arrayLhs: Any, arrayRhs: Any): Any {
        val localClass = arrayLhs.javaClass.componentType
        val i = Array.getLength(arrayLhs)

        val j = i + Array.getLength(arrayRhs)

        val newInstance = Array.newInstance(localClass, j)
        for (k in 0 until j) {
            if (k < i) {
                Array.set(newInstance, k, Array.get(arrayLhs, k))
            } else {
                Array.set(newInstance, k, Array.get(arrayRhs, k - i))
            }
        }
        return newInstance
    }
}

object ReflectUtil {

    fun getField(obj: Any?, clazz: Class<*>?, fieldName: String): Any? {
        val field = clazz?.getDeclaredField(fieldName)
        field?.isAccessible = true
        return field?.get(obj)
    }

    fun setField(obj: Any, clazz: Class<*>, value: Any) {
        val field = clazz.getDeclaredField("dexElements")
        field.isAccessible = true
        field.set(obj, value)
    }

    fun getPathList(baseDexClassLoader: Any?): Any? {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList")
    }

    fun getDexElements(paramObject: Any?): Any? {
        return getField(paramObject, paramObject?.javaClass, "dexElements")
    }

}

object FileUtil {


    /**
     * 文件复制.
     */
    fun copy(srcFile: File, destFile: File): Boolean {
        try {
            val input = FileInputStream(srcFile)
            val out = FileOutputStream(destFile)
            val bytes = ByteArray(1024 * 5)
            var len: Int = 0
            while (len != -1) {
                len = input.read(bytes)
                out.write(bytes, 0, len)
            }
            input.close()
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
            e.e("复制异常")
            return false
        }
        return true
    }

}


object HotFixManager {
    const val DEX_NAME: String = "classes2.dex"
    const val DEX_DIR = "odex"
    const val DEX_SUFFIX = ".dex"

    //保存所有的Dex文件
    private val loadedDex = HashSet<File>()


    init {
        //复制代码之前，先清理集合
        loadedDex.clear()
    }


    //加载Dex文件
    fun loadFixedDex(context: Context) {
        try {
            val fileDir = context.getDir(DEX_DIR, Context.MODE_PRIVATE)
            //循环目录下的所有文件
            val listFiles = fileDir.listFiles()
            for (file in listFiles) {
                file.absolutePath.d("文件夹下所有的文件")
                //过滤得到以.dex结尾的并且名字不是"classes.dex"（主要主dex执行加载dex逻辑）的dex文件，
                if (file.name.endsWith(DEX_SUFFIX) && "classes.dex" != file.name) {
                    loadedDex.add(file)
                }
            }
            createDexClassLoader(context, fileDir)
        } catch (e: Exception) {
            e.printStackTrace()
            e.e("HotFix")
        }
    }

    //创建类加载器
    private fun createDexClassLoader(context: Context, fileDir: File) {
        //创建解压目录
        val optDir = fileDir.absolutePath + File.separator + "opt_dex"
        val optFile = File(optDir)
        if (!optFile.exists())
            optFile.mkdirs()
        for (dex in loadedDex) {
            dex.absolutePath.d("DEX文件")
            //创建加载器
            val dexClassLoader = DexClassLoader(dex.absolutePath, optDir, null, context.classLoader)
            //循环修复
            fix(dexClassLoader, context)
        }
        "修复结束".d()
    }

    private fun fix(dexClassLoader: DexClassLoader, context: Context) {
        val systemClassLoader = context.classLoader as PathClassLoader
        //获取资源的DexElements数组
        val myDexElements = ReflectUtil.getDexElements(ReflectUtil.getPathList(dexClassLoader))
        //获取系统的DexElements数组
        val systemDexElements = ReflectUtil.getDexElements(ReflectUtil.getPathList(systemClassLoader))
        //合并系统的pathList
        val dexElements = FileUtils.combineArray(myDexElements!!, systemDexElements!!)
        //得到系统的pathList
        val pathList = ReflectUtil.getPathList(systemClassLoader)
        //反射将新的pathList设置给系统
        ReflectUtil.setField(pathList!!, pathList.javaClass, dexElements)

    }

}