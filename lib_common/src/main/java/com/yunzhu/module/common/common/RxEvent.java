package com.yunzhu.module.common.common;

public class RxEvent {

    public int what;
    public Object event;

    public RxEvent() { }

    /**
     * RxBus 事件
     * @param what       事件类型
     * @param event      事件对象
     */
    public RxEvent(int what, Object event) {
        this.what = what;
        this.event = event;
    }

    public Object getEvent() {
        return event;
    }

}
