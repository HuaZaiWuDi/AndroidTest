package com.wesmarclothing.jniproject.app

import android.annotation.SuppressLint
import android.content.pm.Signature
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.kongzue.dialog.v2.MessageDialog
import com.kongzue.dialog.v2.WaitDialog
import com.vondear.rxtools.utils.RxAppUtils
import com.vondear.rxtools.utils.RxClipboardUtils
import com.vondear.rxtools.utils.RxLogUtils
import com.vondear.rxtools.utils.RxSignaturesUtils.toHexString
import com.wesmarclothing.jniproject.R
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.toast
import com.wesmarclothing.mylibrary.net.RxComposeUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_applist.*
import java.security.MessageDigest

class APPListActivity : AppCompatActivity() {


    lateinit var adapter: BaseQuickAdapter<RxAppUtils.AppInfo, BaseViewHolder>
    var allAppInfo: List<RxAppUtils.AppInfo>? = null
    var filterText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applist)


        initView()
        initData(false)
    }

    private fun initView() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterText = newText
                if (newText == null) return true
                newText?.d()
                val data = adapter.data

                val toList = data.filter {
                    it.packageName.contains(newText) || it.name.contains(newText)
                }.toList()
                adapter.setNewData(toList)
                return true
            }
        })


        rv_AppList.layoutManager = LinearLayoutManager(this)
        rv_AppList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = object : BaseQuickAdapter<RxAppUtils.AppInfo, BaseViewHolder>(R.layout.item_appinfo) {
            override fun convert(helper: BaseViewHolder?, item: RxAppUtils.AppInfo?) {
                val appInfo = "应用名称：${item?.name}\n" +
                        "应用包名：${item?.packageName}\n" +
                        "版本信息：${item?.versionName}\t|\t${item?.versionCode}\n" +
                        "应用路径：${item?.packagePath}\n" +
                        "Debug模式：${RxAppUtils.isAppDebug(this@APPListActivity, item?.packageName)}\n" +
                        "签名信息：${getSignatures(RxAppUtils.getAppSignature(this@APPListActivity, item?.packageName))}\n" +
                        "SHA1信息：${RxAppUtils.getAppSignatureSHA1(this@APPListActivity, item?.packageName)}\n" +
                        "是否前台：${RxAppUtils.isAppForeground(this@APPListActivity, item?.packageName)}\n"

                helper?.setImageDrawable(R.id.iv_appIcon, item?.icon)
                    ?.setText(R.id.tv_appInfo, appInfo)
            }
        }

        rv_AppList.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as? RxAppUtils.AppInfo
            val textView = view.findViewById<TextView>(R.id.tv_appInfo)

            MessageDialog.show(this, item?.name, textView.text.toString(), "拷贝信息")
            { dialog, index ->
                RxClipboardUtils.copyText(this, textView.text)
                toast("拷贝成功")
            }
        }

        tablayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.text == "第三方APP") {
                    initData(false)
                } else {
                    initData(true)
                }
            }
        })

    }


    private fun getSignatures(signatures: Array<Signature>): String {
        try {
            val digest = MessageDigest.getInstance("MD5")
            if (signatures != null) {
                val var3 = signatures
                val var4 = signatures.size

                for (var5 in 0 until var4) {
                    val s = var3[var5]
                    digest.update(s.toByteArray())
                }
            }

            return toHexString(digest.digest())
        } catch (var7: Exception) {
            RxLogUtils.e(var7)
            return ""
        }

    }

    @SuppressLint("CheckResult")
    private fun initData(isSystem: Boolean) {
//        WaitDialog.dismiss()
//        WaitDialog.show(this, "正在加载")
        "获取app信息$isSystem".d()

        if (allAppInfo != null) {
            val appList = allAppInfo
                ?.filter {
                    if (isSystem)
                        RxAppUtils.isSystemApp(this, it.packageName)
                    else {
                        !RxAppUtils.isSystemApp(this, it.packageName)
                    }
                }
                ?.filter {
                    if (filterText != null)
                        it.packageName.contains(filterText ?: "") || it.name.contains(filterText ?: "")
                    else {
                        true
                    }
                }
                ?.toList()
            adapter.setNewData(appList)
            return
        }

        Observable.create<List<RxAppUtils.AppInfo>> {
            it.onNext(RxAppUtils.getAllAppsInfo(this))
        }
            .compose(RxComposeUtils.rxThreadHelper())
            .doOnSubscribe {
                WaitDialog.dismiss()
                WaitDialog.show(this, "正在加载")
            }
            .subscribe {
                WaitDialog.dismiss()
                allAppInfo = it
                adapter.setNewData(it)
            }
    }


}
