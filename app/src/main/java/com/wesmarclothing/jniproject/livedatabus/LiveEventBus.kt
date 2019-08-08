package com.wesmarclothing.jniproject.livedatabus

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.ExternalLiveData
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.utils.ThreadUtils.isMainThread
import com.wesmarclothing.jniproject.livedatabus.LiveEventBus.ObserverWrapper
import com.wesmarclothing.kotlintools.kotlin.d
import java.util.*
import kotlin.reflect.KClass

/**
 * @Package com.wesmarclothing.kotlintools.kotlin.eventbus
 * @FileName LiveEventBus
 * @Date 2019/7/22 17:07
 * @Author JACK
 * @Describe TODO
 * @Project WeiMiBra
 *
 *
 *
 *   _     _           _____                _  ______
 *  | |   (_)         |  ___|              | | | ___ \
 *  | |    ___   _____| |____   _____ _ __ | |_| |_/ /_   _ ___
 *  | |   | \ \ / / _ \  __\ \ / / _ \ '_ \| __| ___ \ | | / __|
 *  | |___| |\ V /  __/ |___\ V /  __/ | | | |_| |_/ / |_| \__ \
 *  \_____/_| \_/ \___\____/ \_/ \___|_| |_|\__\____/ \__,_|___/
 *
 *
 */


object LiveEventBus {


    //    private val bus: MutableMap<Class<*>, LiveEvent<*>> = mutableMapOf()
    /**
     * 使用Kotlin类型进行引用类型的包装，
     * 因为基本数据类型中Int::class.java返回的是拆箱之后的int
     * post使用泛型之后，基本数据类型变成了Integer装箱类型
     */
    private val bus: MutableMap<KClass<*>, LifecycleLiveData<*>> = mutableMapOf()

    private var lifecycleObserverAlwaysActive = true
    private var autoClear = false
    private val mainHandler = Handler(Looper.getMainLooper())

    @Synchronized
    fun <T : Any> fetch(type: Class<T>): LifecycleLiveData<T> {
        val kotlin = type.kotlin
        if (!bus.containsKey(kotlin)) {
            bus[kotlin] =
                LifecycleLiveData(type)
        }
        bus.keys.d("FETCH")

        return bus[kotlin] as LifecycleLiveData<T>
    }

    fun <T : Any> post(value: T) {
        val type = value::class.java
        val kotlin = value::class.java.kotlin

        if (!bus.containsKey(kotlin)) {
            bus[kotlin] =
                LifecycleLiveData(type)
        }

        bus.keys.d("POST")

        if (isMainThread()) {
            postInternal(value)
        } else {
            mainHandler.post(
                PostValueTask(
                    value
                )
            )
        }
    }


    inline fun <reified T : Any> post(value: Collection<T>) {
        val toTypedArray = value.toTypedArray()
        post(toTypedArray)
    }


    @MainThread
    private fun <T : Any> postInternal(value: T) {
        val type = value::class.java
        val kotlin = value::class.java.kotlin
        val liveData = bus[kotlin] as LifecycleLiveData<T>
        liveData.value = value
    }


    fun postDelay(value: Any, delay: Long) {
        mainHandler.postDelayed(
            PostValueTask(
                value
            ), delay)
    }


    class LifecycleLiveData<T>(key: Class<T>) : ExternalLiveData<T>() {

        private val observerMap = HashMap<Observer<in T>, LiveEventBus.ObserverWrapper<in T>>()
        private var key: Class<T> = key
        private var isSticky: Boolean = false

        override fun observerActiveLevel(): Lifecycle.State {
            return if (lifecycleObserverAlwaysActive) Lifecycle.State.CREATED else Lifecycle.State.STARTED
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            runMainThread {
                val observerWrapper = ObserverWrapper(observer)
                if (!isSticky)
                    observerWrapper.preventNextEvent = version > START_VERSION
                super.observe(owner, observer)
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            runMainThread {
                val observerWrapper = ObserverWrapper(observer)
                if (!isSticky)
                    observerWrapper.preventNextEvent = version > START_VERSION

                observerMap[observer] = observerWrapper
                super.observeForever(observer)
            }
        }

        fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
            isSticky = true
            observe(owner, observer)
        }

        fun observeStickyForever(observer: Observer<T>) {
            isSticky = true
            observeForever(observer)
        }

        override fun removeObserver(observer: Observer<in T>) {
            var realObserver: Observer<in T>? = observer

            runMainThread {
                if (observerMap.containsKey(observer)) {
                    realObserver = observerMap.remove(observer)
                }
            }
            super.removeObserver(realObserver!!)

            if (autoClear && !this.hasObservers()) {
                val kotlin = key::class.java.kotlin
                bus.remove(kotlin)
            }
        }
    }

    private class PostValueTask<T : Any>(private val newValue: T) : Runnable {
        override fun run() {
            postInternal(newValue)
        }
    }

    private class ObserverWrapper<T> internal constructor(private val observer: Observer<T>) : Observer<T> {
        var preventNextEvent = false

        override fun onChanged(t: T?) {
            if (preventNextEvent) {
                preventNextEvent = false
                return
            }
            try {
                observer.onChanged(t)
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }

        }
    }


    fun runMainThread(action: () -> Unit) {
        if (isMainThread()) {
            action()
        } else {
            mainHandler.post {
                action()
            }
        }
    }


}