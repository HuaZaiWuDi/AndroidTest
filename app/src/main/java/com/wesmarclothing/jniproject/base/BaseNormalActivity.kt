package com.wesmarclothing.jniproject.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vondear.rxtools.utils.RxScreenAdapter
import com.vondear.rxtools.view.state.PageLayout

/**
 * Created by luyao
 * on 2018/1/9 14:01
 */
abstract class BaseNormalActivity : AppCompatActivity() {

    var mContext: Context? = null
    var mActivity: AppCompatActivity? = null
    lateinit var pageLayout: PageLayout

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.extras != null) {
            initBundle(intent.extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this
        initStateBar()
        initConfig()
        setContentView(getLayoutResId())
        initView()
        if (intent.extras != null) {
            initBundle(intent.extras)
        }
        initData()

        val contentLayout = findViewById<View>(getContentResId())
        pageLayout = PageLayout.Builder(this)
            .initPage(contentLayout ?: this)
            .setOnRetryListener {
                onStateRefresh()
            }
            .create()

    }

    private fun initConfig() {
        RxScreenAdapter.setCustomDensity(applicationContext as Application, this)
    }


    /**
     * 状态栏初始化
     */
    open fun initStateBar() {}

    /**
     *  刷新状态
     */
    abstract fun onStateRefresh()

    /**
     * 设置布局状态的容器布局
     */
    open fun getContentResId() = -1

    /**
     * 上下文布局
     */
    abstract fun getLayoutResId(): Int

    /**
     * 控件初始化
     */
    abstract fun initView()

    /**
     * 数据初始化
     */
    abstract fun initData()

    /**
     * Bundle初始化
     */
    abstract fun initBundle(mBundle: Bundle)


    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
        mContext = null
    }
}
