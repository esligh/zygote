package com.yunzhu.module.common.vm

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDisposeConverter
import com.yunzhu.module.common.utils.RxLifecycleUtils

/**
 * @author:lisc
 * @date: 2019-10-16
 * @description: VM基类
 * @version 1.0.0
 * @see https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
 *
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel, IBaseView {
    private var lifecycleOwner: LifecycleOwner? = null
    private var mBaseView: BaseViewLiveData? = null
    var pageIntent: Intent? = null


    override fun onCreate(owner: LifecycleOwner) {
        lifecycleOwner = owner
    }

    protected fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        if (null == lifecycleOwner) throw NullPointerException("lifecycleOwner == null")
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner)
    }

    override fun onDestroy(owner: LifecycleOwner) {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}

    override fun onCleared() {
        super.onCleared()
    }

    val baseViewLiveData: BaseViewLiveData
        get() {
            if (mBaseView == null) {
                mBaseView = BaseViewLiveData()
            }
            return mBaseView!!
        }

    override fun showLoading(msgRes: Int) {
        baseViewLiveData.showLoading(msgRes)
    }

    override fun closeLoading() {
        baseViewLiveData.closeLoading()
    }

    override fun closePage() {
        baseViewLiveData.closePage()
    }

    override fun setLoadingMessage(message: String) {
        baseViewLiveData.setLoadingMessage(message)
    }
}