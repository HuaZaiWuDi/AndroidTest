package com.wesmarclothing.jniproject.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wesmarclothing.ems.base.BaseViewModel
import com.wesmarclothing.ems.base.StateConstants
import com.wesmarclothing.jniproject.utils.TUtil
import com.wesmarclothing.kotlintools.kotlin.e

/**
 * Created by luyao
 * on 2019/1/29 10:03
 */
abstract class BaseActivity<VM : BaseViewModel> : BaseNormalActivity() {

    protected lateinit var mViewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        startObserve()

    }

    /**
     * 默认的数据展示
     */
    open fun startObserve() {
        mViewModel.mErrorMessage.observe(this, Observer {
            it.e("全局异常$it")
            errorMessage(it)
        })

        mViewModel.loadState.observe(this, Observer {
            when (it) {
                StateConstants.NOT_NET_WORK_STATE -> {
                    showError(it)
                }
                StateConstants.ERROR_STATE -> {
                    showError(it)
                }
                StateConstants.LOADING_STATE -> {
                    showLoading()
                }
                StateConstants.SUCCESS_STATE -> {
                    showSuccess()
                }
                StateConstants.NOT_DATA_STATE -> {
                    showEmpty()
                }
            }
        })
    }

    private fun initVM() {
        mViewModel = ViewModelProviders.of(this).get(TUtil.getInstance(this, 0) as Class<VM>)
        mViewModel.let(lifecycle::addObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
    }

    /**
     * 展示成功状态
     */
    fun showSuccess() {
        pageLayout.hide()
    }

    /**
     * 展示异常状态
     */
    fun showError(int: Int) {
        val errorView = pageLayout.showError() ?: return

    }

    /**
     * 展示空状态
     */
    fun showEmpty() {
        pageLayout.showEmpty()
    }

    /**
     * 展示加载状态
     */
    fun showLoading() {
        pageLayout.showLoading()
    }

    override fun onStateRefresh() {
        showLoading()
    }

    open fun errorMessage(message: String) {

    }


}