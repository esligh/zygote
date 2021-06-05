package com.yunzhu.module.common.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class BaseViewLiveData : SingleLiveEvent<Any?>(), IBaseView {

    var showDialogEvent: SingleLiveEvent<Int>? = null
        get() {
            if (field == null) {
                field = SingleLiveEvent<Int>()
            }
            return field
        }
        private set

    var dismissDialogEvent: SingleLiveEvent<Void>? = null
        get() {
            if (field == null) {
                field = SingleLiveEvent<Void>()
            }
            return field
        }
        private set

    var finishEvent: SingleLiveEvent<Void>? = null
        get() {
            if (field == null) {
                field = SingleLiveEvent()
            }
            return field
        }
        private set

    var setMessageEvent: SingleLiveEvent<String>? = null
        get() {
            if (field == null) {
                field = SingleLiveEvent<String>()
            }
            return field
        }
        private set

    override fun observe(owner: LifecycleOwner, observer: Observer<in Any?>) {
        super.observe(owner, observer)
    }

    override fun showLoading(msgRes: Int) {
        showDialogEvent!!.value = msgRes
    }

    override fun closeLoading() {
        dismissDialogEvent!!.postValue(null)
    }

    override fun setLoadingMessage(message: String) {
        setMessageEvent!!.postValue(message)
    }

    override fun closePage() {
        finishEvent!!.postValue(null)
    }
}