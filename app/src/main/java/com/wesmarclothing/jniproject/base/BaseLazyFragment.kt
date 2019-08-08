package com.wesmarclothing.ems.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vondear.rxtools.view.state.PageLayout
import com.wesmarclothing.jniproject.utils.TUtil
import com.wesmarclothing.kotlintools.kotlin.d
import com.wesmarclothing.kotlintools.kotlin.e

/**
 * Created by luyao
 * on 2019/1/29 10:57
 */
abstract class BaseLazyFragment<VM : BaseViewModel> : Fragment() {

    /**
     * 是否可见状态
     */
    var isVisibled = false
    /**
     * 标志位，View已经初始化完成。
     */
    var isPrepared = false

    /**
     * 是否第一次加载
     */
    private var isFirstLoad = true
    protected lateinit var mViewModel: VM
    lateinit var pageLayout: PageLayout


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFirstLoad = true

        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (arguments != null) {
            initBundle(arguments!!)
        }
        val contentLayout = view.findViewById<View>(getContentResId())
        pageLayout = PageLayout.Builder(context)
            .initPage(contentLayout ?: this)
            .setOnRetryListener {
                onStateRefresh()
            }
            .create()

        initVM()
        startObserve()
        isPrepared = true
        lazyLoad()
    }

    private fun lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return
        }
        isFirstLoad = false
        initData()
    }

    open fun startObserve() {
        mViewModel.mErrorMessage.observe(this, Observer {
            it.e("全局异常$it")
        })

        mViewModel.loadState.observe(this, Observer {
            when (it) {
                StateConstants.NOT_NET_WORK_STATE -> {
                    showError()
                }
                StateConstants.ERROR_STATE -> {
                    showError()
                }
                StateConstants.LOADING_STATE -> {
                    showLoading()
                }
                StateConstants.SUCCESS_STATE -> {
                    showSuccess()
                }
                StateConstants.NOT_DATA_STATE -> {
                    showError()
                }
            }
        })
    }

    /**
     * 展示成功状态
     */
    protected fun showSuccess() {}

    /**
     * 展示异常状态
     */
    protected fun showError() {}

    /**
     * 展示加载状态
     */
    protected fun showLoading() {}

    /**
     *  刷新状态
     */
    abstract fun onStateRefresh()

    /**
     * 设置布局状态的容器布局
     */
    open fun getContentResId() = -1

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initBundle(mBundle: Bundle)

    private fun initVM() {
        mViewModel = ViewModelProviders.of(this).get(TUtil.getInstance(this, 0) as Class<VM>)
        mViewModel.let(lifecycle::addObserver)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisibled = isVisibleToUser
        if (isPrepared) {
            if (isVisibleToUser) {
                onVisible()
            } else {
                onInvisible()
            }
        }

        "生命周期$lifecycle.currentState".d()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        isVisibled = !hidden
        if (isPrepared) {
            if (!hidden) {
                onVisible()
            } else {
                onInvisible()
            }
        }

        "生命周期2$lifecycle.currentState".d()
    }

    protected fun onVisible() {
        lazyLoad()
    }

    protected fun onInvisible() {}


    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
    }


}