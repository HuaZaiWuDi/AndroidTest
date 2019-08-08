package com.wesmarclothing.ems.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vondear.rxtools.view.state.PageLayout

/**
 * Created by luyao
 * on 2018/9/29 16:17
 */
abstract class BaseNormalFragment : Fragment() {

    lateinit var pageLayout: PageLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (arguments != null) {
            initBundle(arguments!!)
        }

        initData()

        val contentLayout = view.findViewById<View>(getContentResId())
        pageLayout = PageLayout.Builder(context)
            .initPage(contentLayout ?: this)
            .setOnRetryListener {
                onStateRefresh()
            }
            .create()

    }

    /**
     * 设置布局状态的容器布局
     */
    open fun getContentResId() = -1

    /**
     *  刷新状态
     */
    abstract fun onStateRefresh()

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initBundle(mBundle: Bundle)

}