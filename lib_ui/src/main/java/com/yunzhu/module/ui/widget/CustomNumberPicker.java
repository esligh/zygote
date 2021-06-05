package com.yunzhu.module.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class CustomNumberPicker extends NumberPicker {
 
    public CustomNumberPicker(Context context) {
        super(context);
    }
 
    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
 
    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }
 
    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }
 
    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }
 
    public void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextSize(18);
        }
    }
}