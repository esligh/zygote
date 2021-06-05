package com.yunzhu.module.bus.api.repository.base

import androidx.lifecycle.MutableLiveData
import com.yunzhu.module.bus.api.entity.BaseResponse

class StateLiveData<T> : MutableLiveData<BaseResponse<T>>() {}