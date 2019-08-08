package com.wesmarclothing.jniproject.mvvm

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wesmarclothing.kotlintools.kotlin.d
import com.wesmarclothing.kotlintools.kotlin.load


/**
 * @Package com.wesmarclothing.jniproject.mvvm
 * @FileName BindingAdapters
 * @Date 2019/7/19 14:42
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */


@BindingAdapter("bind:text")
fun TextView.text(text: Int) {
    this.text = text.toString()
}


@BindingAdapter("bind:toggleGone")
fun View.toggleGone(isGone: Boolean) {
    if (isGone) this.visibility = View.GONE else View.VISIBLE
}

@BindingAdapter("bind:iv_loadPic")
fun ImageView.loadPic(url: String) {
    this.load(url)
}


@BindingAdapter(
    value = ["bind:rv_onLoadMore", "bind:rv_loadMoreEnable"]
)
fun RecyclerView.setupAdapter(
    loadMoreListener: LoadMoreListener,
    loadMoreEnable: Boolean
) {
    val adapter = this.adapter
    if (adapter == null || adapter !is BaseQuickAdapter<*, *>) {
        return
    }
    adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
        loadMoreListener.onLoadMore()
    }, this)
    adapter.setEnableLoadMore(loadMoreEnable)
    adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
}


@BindingAdapter("bind:rv_onItemClick")
fun RecyclerView.onItemClick(itemClickListener: ItemClickListener) {
    val adapter = this.adapter
    "adapter:$adapter".d()
    if (adapter == null || adapter !is BaseQuickAdapter<*, *>) {
        return
    }

    adapter.setOnItemClickListener { a, view, position ->
        itemClickListener.itemClick(a, view, position)
    }
}


interface ItemClickListener {
    fun itemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int)
}

interface LoadMoreListener {
    fun onLoadMore()
}
