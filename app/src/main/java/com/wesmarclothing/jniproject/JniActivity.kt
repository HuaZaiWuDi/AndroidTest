package com.wesmarclothing.jniproject

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.wesmarclothing.kotlintools.kotlin.*
import kotlinx.android.synthetic.main.activity_jni.*
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.BleManagerCallbacks
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult
import java.util.*

class JniActivity : AppCompatActivity() {

//    init {
//        System.loadLibrary("native-lib")
//    }

    companion object {
        val UUID_SERVICE = "00010203-0405-0607-0809-0a0b0c0d1911"//设置主服务的uuid
        val UUID_NOTIFY = "00010203-0405-0607-0809-0a0b0c0d2b18"//设置可写特征的uuid
        val UUID_WRITE = "00010203-0405-0607-0809-0a0b0c0d2b19"//设置可写特征的uuid

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)

        DEFAULT_LOG_TAG = "【JniActivity】"
        isDebug = true

//        tv_jni.text = JinTest.jnitest()

        Handler().post {
            "Handler".d("执行")
        }

        "onCreate".d("执行")


        tv_jni.text = BuildConfig.VERSION_NAME

        RxPermissionsUtils.requestLoaction(this, object : onRequestPermissionsListener {
            override fun onRequestBefore() {
                "定位权限未申请".d()
            }

            override fun onRequestLater() {
                "定位权限申请成功".d()
            }
        })

        initScan()
//        startAc<MainActivity>()
    }

    private fun connectBle(bledevice: BluetoothDevice) {
        val bleManager = MyBleManager(this)
        bleManager.setGattCallbacks(object : BleManagerCallbacks {
            override fun onDeviceDisconnecting(device: BluetoothDevice) {
                "正在断开连接".d(device.address)
            }

            override fun onDeviceDisconnected(device: BluetoothDevice) {
                "断开连接".d(device.address)
            }

            override fun onDeviceConnected(device: BluetoothDevice) {
                "设备连接".d(device.address)
            }

            override fun onDeviceNotSupported(device: BluetoothDevice) {
                "设备不支持".d(device.address)
            }

            override fun onBondingFailed(device: BluetoothDevice) {
                "绑定失败".d(device.address)
            }

            override fun onServicesDiscovered(device: BluetoothDevice, optionalServicesFound: Boolean) {
                "发现服务".d(device.address)
            }

            override fun onBondingRequired(device: BluetoothDevice) {
                "需要绑定".d(device.address)
            }

            override fun onLinkLossOccurred(device: BluetoothDevice) {
                "断开连接时".d(device.address)
            }

            override fun onBonded(device: BluetoothDevice) {
                "绑定".d(device.address)
            }

            override fun onDeviceReady(device: BluetoothDevice) {
                "设备准备".d(device.address)
            }

            override fun onError(device: BluetoothDevice, message: String, errorCode: Int) {
                "异常:$message:状态码：$errorCode".e(device.address)
            }

            override fun onDeviceConnecting(device: BluetoothDevice) {
                "正在连接".d(device.address)
            }
        })

        bleManager.connect(bledevice)
            .retry(3, 100)
            .timeout(10 * 1000)
            .useAutoConnect(true)
            .enqueue()
    }

    class MyBleManager(context: Context) : BleManager<BleManagerCallbacks>(context) {

        var writeChar: BluetoothGattCharacteristic? = null
        var notifyChar: BluetoothGattCharacteristic? = null

        override fun getGattCallback() = mBleCallback


        private val mBleCallback = object : BleManagerGattCallback() {

            override fun initialize() {
                super.initialize()
                setNotificationCallback(notifyChar)
                    .with { device, data ->
                        data.value?.toHexString()?.d(device.address)

//                        val bytes = data.value!!

//                        if (bytes[2].toInt() == 0x07) {
//                            /**
//                             * 01 心率 1byte  7,8
//                             * 02 温度 1byte  9,10
//                             * 03 计步 2byte  11,13
//                             * 04 电压 2byte  14,16
//                             * 05 配速 2byte  17,19
//                             */
//
//                            "心率:${bytes[8]}---温度:${bytes[10]}---计步:${Util.bytesToIntLittle2(
//                                bytes[12],
//                                bytes[13]
//                            )}".d(device.address)
//                        }
//                        if (bytes[2].toInt() == 0x09) {
//                            val firmwareVersion = bytes[9].toString() + "." + bytes[10] + "." + bytes[11]
//                            "固件版本号：:$firmwareVersion".d(device.address)
//                        }
                    }
                enableNotifications(notifyChar)
                    .done { device ->
                        "开启通知成功".d(device.address)
                    }
                    .fail { device, status ->
                        "开启通知失败状态:$status".d(device.address)
                    }
                    .enqueue()
//                val bytes = ByteArray(20)
//                bytes[0] = 0x40
//                bytes[1] = 0x11
//                bytes[2] = 0x09
//                writeCharacteristic(writeChar, bytes)
//                    .done { device ->
//                        "写成功".d(device.address)
//                    }
//                    .fail { device, status ->
//                        "写失败状态:$status".d(device.address)
//                    }
//                    .enqueue()
            }

            override fun onDeviceDisconnected() {
                writeChar = null
                notifyChar = null
            }

            override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
                val service = gatt.getService(UUID.fromString(MainActivity.UUID_Servie))
                writeChar =
                    service?.getCharacteristic(UUID.fromString(MainActivity.UUID_CHART_WRITE))
                notifyChar =
                    service?.getCharacteristic(UUID.fromString(MainActivity.UUID_CHART_READ_NOTIFY))
                return service != null && writeChar != null && notifyChar != null
            }

        }

    }


    private fun initScan() {
//        val scanFilter = ScanFilter.Builder()
//            .setServiceUuid(ParcelUuid.fromString(MainActivity.UUID_Servie))
//            .build()

//        val list = mutableListOf(scanFilter)


        if (!isBLEEnabled()) {
            enableBLE()
            return
        }

        BluetoothLeScannerCompat.getScanner().startScan(null, null, object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                "扫描到设备".d(result.device.name ?: "noName")
                if ("WeSma".equals(result.device.name)) {
                    BluetoothLeScannerCompat.getScanner().stopScan(this)
                    connectBle(result.device)
                }
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                super.onBatchScanResults(results)
                "扫描到设备".d(results.firstOrNull()?.device?.name ?: "")
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                "扫描失败状态码：$errorCode".d()
            }

        })
    }

    private fun isBLEEnabled(): Boolean {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        return adapter != null && adapter.isEnabled
    }

    private fun enableBLE() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager?.adapter?.enable()
    }

    private fun disableBLE() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager?.adapter?.disable()
    }


    override fun onStart() {
        super.onStart()
        "onStart".d("执行")
    }

    override fun onResume() {
        super.onResume()
        "onResume".d("执行")
    }

//    private external fun jniTest(s: String): String
//
//    external fun stringFromJNI(): String
//
//
//    external fun jnitest(): String
}
