package com.yunzhu.module.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

//为每个键盘按钮设置一个安全的AccessibilityDelegate防止具有accessibility权限的应用监听
public class SecureAccessibilityDelegate extends View.AccessibilityDelegate{

    public SecureAccessibilityDelegate() {
        super();
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        return true;
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
        return null;
    }

    @Override
    public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {

    }

    @Override
    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
    }

    @Override
    public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
        return false;
    }

    @Override
    public void sendAccessibilityEvent(View host, int eventType) {
    }

    @Override
    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        return true;
    }

    @Override
    public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
    }
}