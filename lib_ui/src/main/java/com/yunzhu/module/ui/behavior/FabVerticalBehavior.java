package com.yunzhu.module.ui.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yunzhu.module.ui.behavior.anim.FabVerticalBehaviorAnim;

public class FabVerticalBehavior extends CommonBehavior implements IBehavior {

    public FabVerticalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    public IBehaviorAnim createBehaviorAnim(CoordinatorLayout coordinatorLayout, View child) {
        return new FabVerticalBehaviorAnim(coordinatorLayout, child);
    }
}
