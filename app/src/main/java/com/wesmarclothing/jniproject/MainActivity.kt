package com.wesmarclothing.jniproject

import android.bluetooth.BluetoothGatt
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleMtuChangedCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleScanAndConnectCallback
import com.clj.fastble.callback.BleWriteCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.wesmarclothing.jniproject.dfu.DfuService
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.e
import kotlinx.android.synthetic.main.activity_main.*
import no.nordicsemi.android.dfu.DfuProgressListenerAdapter
import no.nordicsemi.android.dfu.DfuServiceInitiator
import no.nordicsemi.android.dfu.DfuServiceListenerHelper
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {


    val BleDeivceList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bleText.movementMethod = ScrollingMovementMethod.getInstance()

        initBle()
//        initDoraemonKit()
    }

    private fun initDoraemonKit() {
//        DoraemonKit.install(this.application)
//        DoraemonKit.setWebDoorCallback { context, url ->
//            url.d()
//        }
    }


    fun TextView.appText(text: String) {
        this.post {
            this.append(text)
            val scrollAmount = this.layout.getLineTop(this.lineCount) - this.height

            if (scrollAmount > 0)
                this.scrollTo(0, scrollAmount)
            else
                this.scrollTo(0, 0)
        }
    }

    private fun initBle() {
        BleManager.getInstance().apply {
            init(application)

            if (!isBlueEnable)
                enableBluetooth()
            enableLog(false)//是否开启蓝牙日志
            maxConnectCount = 1
            operateTimeout = 3000//设置操作超时时间
            setReConnectCount(3, 3000.toLong())
            connectOverTime = 10000.toLong()
        }
    }


    override fun onResume() {
        super.onResume()
        initScan()
    }

    fun reScan(view: View) {
        initScan()
    }

    private fun initScan() {
        RxPermissionsUtils.requestLoaction(this, object : onRequestPermissionsListener {
            override fun onRequestBefore() {
                "定位权限未申请".d()
            }

            override fun onRequestLater() {
                "定位权限申请成功".d()
            }
        })
        bleText.text = ""
        val config = BleScanRuleConfig.Builder().apply {
            setServiceUuids(arrayOf(UUID.fromString(UUID_Servie)))
            setScanTimeOut(0)
        }.build()
        BleManager.getInstance().initScanRule(config)

        BleManager.getInstance()
            .scanAndConnect(object : BleScanAndConnectCallback() {
                override fun onStartConnect() {
                    bleText.appText("开始连接\n")
                    "开始连接".d()
                }

                override fun onScanStarted(success: Boolean) {
                    bleText.appText("开始扫描\n")
                    "开始扫描".d()
                }

                override fun onDisConnected(
                    isActiveDisConnected: Boolean,
                    device: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    bleText.appText("断开连接:${device?.mac}\n")
                    "断开连接".d()
                    initScan()
                }

                override fun onConnectSuccess(bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                    bleText.appText("连接成功:${bleDevice?.mac}\n")
                    "连接成功:${bleDevice?.mac}".d()
//                    connectSuccess(bleDevice)
//                    startMyDFU(null, bleDevice)
                }


                override fun onScanFinished(scanResult: BleDevice?) {
                    bleText.appText("扫描结束:${scanResult?.mac}\n")
                    "扫描结束:${scanResult?.mac}".d()
                }

                override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                    bleText.appText("连接失败:${bleDevice?.mac}\n")
                    "连接失败:${bleDevice?.mac}".d()
                }

                override fun onScanning(bleDevice: BleDevice?) {
                    bleText.appText("扫描发现:${bleDevice?.mac}\n")
                    "扫描发现:${bleDevice?.mac}".d()
                }
            })
    }


    fun connectSuccess(bleDevice: BleDevice?) {
//        bleText.appText("打开消息通知:${bleDevice?.mac}\n")
        BleManager.getInstance().notify(bleDevice, UUID_Servie, UUID_CHART_READ_NOTIFY, object : BleNotifyCallback() {
            override fun onCharacteristicChanged(data: ByteArray?) {
                if (data == null) return
                if (data[2].toInt() == 0x07) {
                    /**
                     * 01 心率 1byte  7,8
                     * 02 温度 1byte  9,10
                     * 03 计步 2byte  11,13
                     * 04 电压 2byte  14,16
                     * 05 配速 2byte  17,19
                     */

//                    bleText.appText(
//                        "心率:${data[8]}---温度:${data[10]}---计步:${Util.bytesToIntLittle2(
//                            data[12],
//                            data[13]
//                        )}\n"
//                    )
                }
                if (data[2].toInt() == 0x09) {
                    val firmwareVersion = data[9].toString() + "." + data[10] + "." + data[11]
                    bleText.appText("固件版本号：:$firmwareVersion\n")
                }
            }

            override fun onNotifyFailure(exception: BleException?) {
//                bleText.appText("消息通知打开失败:${bleDevice?.mac}\n")
                BleManager.getInstance().disconnect(bleDevice)
            }

            override fun onNotifySuccess() {
//                bleText.appText("消息通知打开成功:${bleDevice?.mac}\n")
                BleManager.getInstance().setMtu(bleDevice, 200, object : BleMtuChangedCallback() {
                    override fun onMtuChanged(mtu: Int) {
                        val bytes = ByteArray(20)
                        bytes[0] = 0x40
                        bytes[1] = 0x11
                        bytes[2] = 0x09
                        writeBle(bleDevice, bytes)
//                        bleText.appText("MTU更改成功:$mtu:${bleDevice?.mac}\n")
                    }

                    override fun onSetMTUFailure(exception: BleException?) {
//                        bleText.appText("MTU更改失败:${bleDevice?.mac}\n")
                        BleManager.getInstance().disconnect(bleDevice)
                    }
                })
            }
        })
    }

    fun writeBle(bleDevice: BleDevice?, bytes: ByteArray) {
        BleManager.getInstance().write(bleDevice, UUID_Servie, UUID_CHART_WRITE, bytes, object : BleWriteCallback() {
            override fun onWriteSuccess(current: Int, total: Int, justWrite: ByteArray?) {
//                bleText.appText("写成功:${bleDevice?.mac}\n")
            }

            override fun onWriteFailure(exception: BleException?) {
//                bleText.appText("写失败:${bleDevice?.mac}\n")
                BleManager.getInstance().disconnect(bleDevice)
            }
        })
    }

    companion object {
        val UUID_CHART_READ_NOTIFY = "6E400003-B5A3-F393-E0A9-E50E24DCCA9E".toLowerCase()

        val UUID_Servie = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E".toLowerCase()

        val UUID_CHART_WRITE = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E".toLowerCase()
    }

    override fun onDestroy() {
        DfuServiceListenerHelper.unregisterProgressListener(this, listenerAdapter)
        super.onDestroy()
    }

    override fun onStart() {
        DfuServiceListenerHelper.registerProgressListener(this, listenerAdapter)
        super.onStart()
    }

    private fun startMyDFU(o: File?, bleDevice: BleDevice?) {

        val starter = DfuServiceInitiator(bleDevice?.mac ?: "")
            .setDeviceName(bleDevice?.name)
        starter.setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true)
        //        starter.setZip(R.raw.nrf52832_xxaa_app_7);
        starter.setZip(R.raw.v1_0_up_808090)
        starter.start(this, DfuService::class.java!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DfuServiceInitiator.createDfuNotificationChannel(this)
        }
    }


    private val listenerAdapter = object : DfuProgressListenerAdapter() {
        override fun onDeviceConnected(deviceAddress: String?) {
            super.onDeviceConnected(deviceAddress)
            deviceAddress?.d("onDeviceConnected")
        }

        override fun onDeviceDisconnected(deviceAddress: String?) {
            super.onDeviceDisconnected(deviceAddress)
            deviceAddress?.d("onDeviceDisconnected")
        }

        override fun onDfuAborted(deviceAddress: String?) {
            super.onDfuAborted(deviceAddress)
            deviceAddress?.d("onDfuAborted")
        }

        override fun onDfuProcessStarted(deviceAddress: String?) {
            super.onDfuProcessStarted(deviceAddress)
            deviceAddress?.d("onDfuProcessStarted")
        }

        override fun onDfuProcessStarting(deviceAddress: String?) {
            super.onDfuProcessStarting(deviceAddress)
            deviceAddress?.d("onDfuProcessStarting")

            bleText.appText("固件升级开始：\n")
        }

        override fun onDeviceConnecting(deviceAddress: String?) {
            super.onDeviceConnecting(deviceAddress)
            deviceAddress?.d("onDeviceConnecting")
        }

        override fun onDeviceDisconnecting(deviceAddress: String?) {
            super.onDeviceDisconnecting(deviceAddress)
            deviceAddress?.d("onDeviceDisconnecting")
        }

        override fun onDfuCompleted(deviceAddress: String?) {
            super.onDfuCompleted(deviceAddress)
            deviceAddress?.d("onDfuCompleted")

            bleText.appText("固件升级成功：\n")
            BleDeivceList.add(deviceAddress ?: "")
        }

        override fun onEnablingDfuMode(deviceAddress: String?) {
            super.onEnablingDfuMode(deviceAddress)
            deviceAddress?.d("onEnablingDfuMode")
        }

        override fun onError(deviceAddress: String?, error: Int, errorType: Int, message: String?) {
            super.onError(deviceAddress, error, errorType, message)
            deviceAddress?.e("onError:" + message!!)
            bleText.appText("固件升级失败：\n")
        }

        override fun onFirmwareValidating(deviceAddress: String?) {
            super.onFirmwareValidating(deviceAddress)
            deviceAddress?.d("onFirmwareValidating")
        }

        override fun onProgressChanged(
            deviceAddress: String?,
            percent: Int,
            speed: Float,
            avgSpeed: Float,
            currentPart: Int,
            partsTotal: Int
        ) {
            super.onProgressChanged(deviceAddress, percent, speed, avgSpeed, currentPart, partsTotal)
            deviceAddress?.d("onProgressChanged:percent:$percent----$speed----avgSpeed:$avgSpeed-----currentPart:$currentPart------prtsTotal:$partsTotal")
            bleText.appText("固件升级进度：:$percent\n")
        }
    }

}
