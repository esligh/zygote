package com.yunzhu.module.ui.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yunzhu.module.ui.behavior.anim.BottomBehaviorAnim;

public class BottomBehavior extends CommonBehavior implements IBehavior {

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    public IBehaviorAnim createBehaviorAnim(CoordinatorLayout coordinatorLayout, View child) {
        return new BottomBehaviorAnim(child);
    }
}
