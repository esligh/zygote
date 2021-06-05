package com.yunzhu.module.common.common;

public enum RefreshResult {
    SUCCEED(1), FAILED(2), NO_DATA(3), NO_MORE(4);

    private final int value;

    RefreshResult(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}