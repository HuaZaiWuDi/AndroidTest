package com.wesmarclothing.jniproject.mvvm

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vondear.rxtools.utils.ToolsProvider
import com.vondear.rxtools.utils.bitmap.RxImageTakerHelper
import com.wesmarclothing.jniproject.R
import com.wesmarclothing.jniproject.RxPermissionsUtils
import com.wesmarclothing.jniproject.databinding.ActivityLoginBinding
import com.wesmarclothing.jniproject.databinding.ItemBinding
import com.wesmarclothing.jniproject.livedatabus.LiveEventBus
import com.wesmarclothing.jniproject.onRequestPermissionsListener
import com.wesmarclothing.kotlintools.kotlin.d
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            com.wesmarclothing.jniproject.R.layout.activity_login
        )
    }

    private val adapter by lazy {
        object : BaseQuickAdapter<String, BaseViewHolder>(com.wesmarclothing.jniproject.R.layout.item) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                val bind = DataBindingUtil.bind<ItemBinding>(helper?.itemView!!)
                bind?.tvItem?.text = item
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.adapter = adapter

        val observableArrayList = ObservableArrayList<String>()
        val arrayListOf = arrayListOf("1", "2", "3", "4", "5")
        observableArrayList.addAll(arrayListOf)
        binding.list = observableArrayList

//        adapter.setNewData(listOf("1", "2", "3", "4", "5"))

        RxPermissionsUtils.requestPermission(
            this,
            Manifest.permission.READ_PHONE_STATE,
            object : onRequestPermissionsListener {
                override fun onRequestBefore() {
                }

                override fun onRequestLater() {
                }
            })

        adapter.setOnItemClickListener { a, view, i ->
            //            viewModel.itemClick(a, view, i)

            when (i) {
                0 -> RxImageTakerHelper.openCamera(
                    this,
                    ToolsProvider.getFileProviderName(this)
                )
                1 -> RxImageTakerHelper.openAlbum(this)
            }
        }

        viewModel.testJson.observe(this) {
            binding.testJson = it
//            startAc<Login2Activity>()
        }


        val testStr =
            "废弃的标签也要学，但是后续在CSS3中会讲解其替代的方式，还在用旧的标签的最主要的原因是：可能有的网页早已经写好，并且没有人去维护了，但是浏览器不能不支持这样的网页，但是在新写的网页中不要再使用废弃的元素。"

//        tv_test.htmlText(
//            testStr, "#D81B60", 30, true,
//            true, true, true, "CSS3", "可能", "的"
//        )

//        initLiveBus()
        initFragment()

        btn_changed.setOnClickListener {
            if (!TestFragment.fragment.isHidden)
                hide(TestFragment.fragment)
            else
                show(TestFragment.fragment)
        }

    }

    private fun initFragment() {
        add(
            R.id.fl_Fragment, TestFragment.fragment,
            arrayOf(
                "index" to 1
            )
        )

//        replace(
//            R.id.fl_Fragment, TestFragment.fragment,
//            arrayOf(
//                "index" to 2
//            )
//        )
    }

    internal class TestFragment : Fragment() {

        var index: Int = 0

        companion object {
            val fragment = TestFragment()
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val textView = TextView(activity)
            index = arguments?.getInt("index") ?: 0
            textView.text = "【TestFragment】:$index"
            return textView
        }

        override fun setUserVisibleHint(isVisibleToUser: Boolean) {
            super.setUserVisibleHint(isVisibleToUser)

            "生命周期$index${lifecycle.currentState}".d()
        }

        override fun onHiddenChanged(hidden: Boolean) {
            super.onHiddenChanged(hidden)

            "2生命周期$index${lifecycle.currentState}".d()
        }

    }


    private fun initLiveBus() {
        LiveEventBus.fetch(String::class.java)
            .observe(this, Observer {
                it.d()
            })

//        LiveEventBus.fetch(LoginViewModel.Item::class.java)
//            .observe(this, Observer {
//                it.d("Item")
//            })

        LiveEventBus.fetch(Int::class.java)
            .observe(this, Observer {
                it.d("Int")
            })

        LiveEventBus.fetch(Double::class.java)
            .observe(this, Observer {
                it.d("Double")
            })

        LiveEventBus.fetch(Float::class.java)
            .observe(this, Observer {
                it.d("Float")
            })

        LiveEventBus.fetch(Short::class.java)
            .observe(this, Observer {
                it.d("Short")
            })

        LiveEventBus.fetch(Char::class.java)
            .observe(this, Observer {
                it.d("Char")
            })

        LiveEventBus.fetch(Long::class.java)
            .observe(this, Observer {
                it.d("Long")
            })

        LiveEventBus.fetch(Boolean::class.java)
            .observe(this, Observer {
                it.d("Boolean")
            })
        LiveEventBus.fetch(Array<String>::class.java)
            .observe(this, Observer {
                for (i in it)
                    i.d("Array<String>")
            })

        LiveEventBus.fetch(Array<Int>::class.java)
            .observe(this, Observer {
                for (i in it)
                    i.d("Array<Int>")
            })


        btn_changed.setOnClickListener {
            LiveEventBus.post("123")
//            LiveEventBus.post(LoginViewModel.Item(11))

            LiveEventBus.post(123)
            LiveEventBus.post(123.23)
            LiveEventBus.post(123.2f)
            LiveEventBus.post(123.2.toShort())
            LiveEventBus.post(123L)
            LiveEventBus.post('c')
            LiveEventBus.post(false)
            LiveEventBus.post(arrayOf("11", "22"))
            LiveEventBus.post(arrayOf(1, 2))

            LiveEventBus.post(listOf(1, 2, 3))

            LiveEventBus.post(mapOf("map1" to 1, "map2" to 2))

            LiveEventBus.post(setOf("set1", "set2", "set3"))

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestCode.d()
        resultCode.d()
        data?.d()
        when (requestCode) {
            0x11 -> RxImageTakerHelper.readBitmapFromAlbumResult(this, data).d()
            0x10 -> RxImageTakerHelper.readBitmapFromCameraResult(this, data).d()
        }
    }
}

fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, action: (T) -> Unit) {
    liveData.observe(this, Observer {
        action(it)
    })
}

fun <T> MutableLiveData<T>.observe(owner: LifecycleOwner, action: (T) -> Unit) {
    observe(owner, Observer {
        action(it)
    })
}