package com.wesmarclothing.ems.base

import androidx.lifecycle.*
import com.vondear.rxtools.utils.RxNetUtils
import com.wesmarclothing.ems.base.StateConstants.ERROR_STATE
import com.wesmarclothing.ems.base.StateConstants.NOT_NET_WORK_STATE
import com.wesmarclothing.ems.data.net.HandleNetExceptionManager
import com.wesmarclothing.jniproject.App
import com.wesmarclothing.kotlintools.kotlin.utils.d
import com.wesmarclothing.kotlintools.kotlin.utils.e
import com.wesmarclothing.mylibrary.net.ExplainException
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by luyao
 * on 2019/1/29 9:58
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {


    val mErrorMessage: MutableLiveData<String> = MutableLiveData()
    var loadState: MutableLiveData<Int> = MutableLiveData()


    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {},
        handleCancellationExceptionManually: Boolean = true
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }


    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    val error = HandleNetExceptionManager.handleResponseError(e)
                    mErrorMessage.value = error
                    e.e("其他异常")
                    catchBlock(e)
                    if (!RxNetUtils.isConnected(App.mApp)) {
                        loadState.value = NOT_NET_WORK_STATE
                    } else
                        loadState.value = ERROR_STATE
                } else {
                    e.e("关闭异常")
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(
        response: WanResponse<Any>,
        successBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (response.errorCode != 0) errorBlock()
            else successBlock()
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        "onCreate".d()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        "onDestroy".d()
    }
}

/**
 * @ClassName: CoroutineExt
 * @Description:网络请求返回结果统一处理
 * @Author: ChenYy
 * @Date: 2019-05-06 17:50
 */
suspend fun <T> WanResponse<T>.awaitResponse(catchBlock: suspend (Throwable) -> Unit = {}): T? {
    var result: T? = null
    try {
        result = suspendCancellableCoroutine<T> { cont ->
            if (this.errorCode == 0) {
                if (this.data != null) {
                    cont.resume(this.data)
                }
            } else {
                cont.resumeWithException(ExplainException(this.errorMsg, -1))
            }
        }
    } catch (e: Throwable) {
        catchBlock(e)
        return result
    }
    return result
}