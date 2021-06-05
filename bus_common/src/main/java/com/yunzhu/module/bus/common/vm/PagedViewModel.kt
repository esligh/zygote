package com.yunzhu.module.bus.common.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData

/**
 * @author Lee
 * @date 2019-10-31
 * @desc 处理分页的ViewModel
 */
abstract class PagedViewModel<T>(application: Application): FrameBaseViewModel(application)
{
    var mData:MutableLiveData<ArrayList<T>> = MutableLiveData()
    var curPage:Int = 1
    var noMoreLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var refreshLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var loadMoreLiveData: MutableLiveData<Boolean> = MutableLiveData()

    open fun pageSize() = 10

    open fun loadData(pageIndex:Int)
    {
        loadData(pageIndex){
            closeLoading()
            if(it != null){
                if(pageIndex == 1) {
                    mData.value?.clear()
                }
                if (mData.value == null) {
                    mData.value = it
                } else {
                    mData.value?.addAll(it)
                    mData.value = mData.value
                }
            }else{
                mData.value?.clear()
            }
            noMoreLiveData.value = (it != null && it.size < pageSize())
            if(pageIndex == 1) {
                refreshLiveData.value = true
            } else {
                loadMoreLiveData.value = true
            }
        }
    }

    abstract fun loadData(page: Int, onResponse: (ArrayList<T>?) -> Unit)

    open fun refresh()
    {
        curPage = 1
        loadData(1)
    }

    open fun loadMore()
    {
        ++curPage
        loadData(curPage)
    }

    open fun refreshItem(pos:Int,item:T)
    {
        val size = mData.value?.size ?: 0
        if(pos in 0 until size){
            mData.value!![pos] = item
            mData.value = mData.value
        }
    }
}
