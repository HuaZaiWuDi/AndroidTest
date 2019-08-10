package com.wesmarclothing.jniproject

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.meituan.robust.Patch
import com.meituan.robust.PatchExecutor
import com.meituan.robust.RobustCallBack
import com.meituan.robust.patch.annotaion.Add
import com.meituan.robust.patch.annotaion.Modify
import com.wesmarclothing.jniproject.hotfix.HotFixManager
import com.wesmarclothing.jniproject.robust.PatchManipulateImp
import com.wesmarclothing.jniproject.utils.FileUtils
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.isDebug
import com.wesmarclothing.kotlintools.kotlin.utils.toast
import kotlinx.android.synthetic.main.activity_simple.*
import java.io.File

class SimpleActivity : AppCompatActivity() {


    @Modify
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_simple)

        isDebug = true


//        GlobalScope.launch {
//            async {
//                delay(5000)
//                "当前线程1：${Thread.currentThread().name}".d()
//            } main {
//                //                textView.text = "当前值：$it"
//                "当前线程2：${Thread.currentThread().name}".d()
//            }
//        }


        RxPermissionsUtils.requestReadExternalStorage(this, object : onRequestPermissionsListener {
            override fun onRequestLater() {
            }

            override fun onRequestBefore() {
            }
        })

        RxPermissionsUtils.requestWriteExternalStorage(this, object : onRequestPermissionsListener {
            override fun onRequestLater() {
            }

            override fun onRequestBefore() {
            }
        })

        crash.setOnClickListener {


            click()
        }

        fix.setOnClickListener {
            val sourceFile = File(Environment.getExternalStorageDirectory(), HotFixManager.DEX_NAME)
            sourceFile.absolutePath.d("本地Dex文件名称")

            if (!sourceFile.exists()) {
                toast("本地文件不存在")
            }

            val targetFile = File(
                getDir(
                    HotFixManager.DEX_DIR,
                    Context.MODE_PRIVATE
                ).absolutePath + File.separator + HotFixManager.DEX_NAME
            )

            targetFile.absolutePath.d("内部Dex文件名称")
            if (targetFile.exists()) {
                targetFile.delete()
                toast("删除成功")
            }
            //复制到私有目录下
            val copy = FileUtils.copy(sourceFile, targetFile)
            "复制$copy".d()

            //修复
            HotFixManager.loadFixedDex(this)
        }
    }

    public fun click() {
        FileUtils().test()
        val i = 10 / 1
        toast("计算结果$i")
    }

    private fun runRobust() {
        PatchExecutor(applicationContext, PatchManipulateImp(), object : RobustCallBack {
            override fun onPatchApplied(result: Boolean, patch: Patch?) {
                "补丁结果onPatchApplied:$result".d(patch?.name ?: "Robust")
            }

            override fun onPatchListFetched(result: Boolean, isNet: Boolean, patches: MutableList<Patch>?) {
                "补丁结果onPatchListFetched:$result 是否是网络$isNet".d("Robust")
            }

            override fun onPatchFetched(result: Boolean, isNet: Boolean, patch: Patch?) {
                "补丁结果onPatchFetched:$result 是否是网络$isNet".d("Robust")
            }

            override fun logNotify(log: String?, where: String?) {
                "补丁结果logNotify:$log ----$where".d("Robust")
            }

            override fun exceptionNotify(throwable: Throwable?, where: String?) {
                "补丁结果exceptionNotify:${throwable.toString()} ----$where".d("Robust")
            }
        }).start()
    }

    /**
     * 需要修复的包
     * */
    @Add
    fun fix() {
//        tv_patch.text = "补丁之前:${BuildConfig.VERSION_NAME}"
        tv_patch.text = "修复之后:${BuildConfig.VERSION_NAME}" + addClass().name
        addModel()
    }

    @Add
    fun addModel() {
        toast("我是新增的方法")
        addModel2()
    }

    @Add
    fun addModel2() {
    }


    @Add
    class addClass {
        val name: String = "addClass"
    }


    fun test() {
        crash.setOnClickListener {

        }

        mapOf("2" to 2, "3" to 3, "4" to 4)

        "2" == "3"

        2 - 3

        val of = listOf(1, 2, 3, 4)

        1 into of

    }

    infix fun <T> T.into(other: Collection<T>): Boolean = other.contains(this)


    private fun clickView(view: View) {
        when (view) {
            crash -> {

            }
        }
    }


    fun <V : View> View.viewClick(action: (View) -> Unit) {
        this.setOnClickListener {
            action(it)
        }
    }

}
