package com.yunzhu.module.ui.behavior.anim;

import android.animation.ValueAnimator;
import android.view.View;

import com.yunzhu.module.ui.behavior.IBehaviorAnim;

public class FabVerticalBehaviorAnim extends AbsBehaviorAnim implements IBehaviorAnim {

    private float mViewY;
    private View mFabView;
    private float mOriginalY;

    public FabVerticalBehaviorAnim(View parentView, View fabView) {
        mFabView = fabView;
        if (parentView != null && fabView != null) {
            mViewY = parentView.getHeight() - fabView.getY();
            mOriginalY = fabView.getY();
        }
    }

    @Override
    public void show() {
        ValueAnimator animator = ValueAnimator.ofFloat(mFabView.getY(), mOriginalY);
        animator.setDuration(600);
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFabView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void hide() {
        ValueAnimator animator = ValueAnimator.ofFloat(mOriginalY, mOriginalY + mViewY);
        animator.setDuration(600);
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFabView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }
}
