package com.yunzhu.module.ui.behavior;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public interface IBehavior {

    @NonNull
    IBehaviorAnim createBehaviorAnim(CoordinatorLayout coordinatorLayout, View child);

    void show();

    void hide();

}
