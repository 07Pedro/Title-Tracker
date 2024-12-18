package com.example.mybookapp.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;

public class titleAnimations {

    public static void startPulseAnimation(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 1.0f);

        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.RESTART);

        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);

        AnimatorSet pulseAnimation = new AnimatorSet();
        pulseAnimation.playTogether(scaleX, scaleY);
        pulseAnimation.setDuration(3000);

        pulseAnimation.start();
    }

    public static void startRotationAnimation(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);

        rotation.setDuration(1000);
        rotation.setInterpolator(new BounceInterpolator());
        rotation.setRepeatCount(0);
        rotation.setRepeatMode(ObjectAnimator.RESTART);

        rotation.start();
    }

}
