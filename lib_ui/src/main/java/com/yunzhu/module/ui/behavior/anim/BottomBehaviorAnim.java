package com.yunzhu.module.ui.behavior.anim;

import android.animation.ValueAnimator;
import android.view.View;

import com.yunzhu.module.ui.behavior.IBehaviorAnim;

public class BottomBehaviorAnim extends AbsBehaviorAnim implements IBehaviorAnim {

    private View mBottomView;
    private float mOriginalY;

    public BottomBehaviorAnim(View bottomView) {
        mBottomView = bottomView;
        mOriginalY = mBottomView.getY();
    }

    @Override
    public void show() {
        ValueAnimator animator = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void hide() {
        ValueAnimator animator = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY + mBottomView.getHeight());
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }
}
