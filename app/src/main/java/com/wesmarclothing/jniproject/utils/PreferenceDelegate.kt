package com.wesmarclothing.jniproject.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @Package com.wesmarclothing.jniproject.utils
 * @FileName PreferenceDelegate
 * @Date 2019/8/14 11:29
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */

class PreferenceDelegate<T>(
    private val context: Context,
    private val name: String,
    private val default: T,
    private val prefName: String = "default"
) : ReadWriteProperty<Any?, T> {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("setValue from delegate")
        return getPreference(key = name)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("setValue from delegate")
        putPreference(key = name, value = value)
    }

    private fun getPreference(key: String): T {
        return when (default) {
            is String -> prefs.getString(key, default)
            is Long -> prefs.getLong(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is Float -> prefs.getFloat(key, default)
            is Int -> prefs.getInt(key, default)
            is Byte -> prefs.getString(key, default.toString()).toByte()
            is Collection<*> -> prefs.getString(key, default.toString()).toByte()
            else -> throw IllegalArgumentException("Unknown Type.")
        } as T
    }

    private fun putPreference(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Byte -> putString(key, value.toString())
            else -> throw IllegalArgumentException("Unknown Type.")
        }
    }.apply()

}
