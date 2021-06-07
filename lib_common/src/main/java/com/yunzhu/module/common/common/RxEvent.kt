package com.yunzhu.module.common.common

class RxEvent {
    var what = 0
    var event: Any? = null

    constructor() {}

    /**
     * RxBus 事件
     * @param what       事件类型
     * @param event      事件对象
     */
    constructor(what: Int, event: Any?) {
        this.what = what
        this.event = event
    }
}