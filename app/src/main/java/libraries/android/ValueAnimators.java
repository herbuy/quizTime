package libraries.android;
import android.animation.ValueAnimator;
import android.view.View;

public class ValueAnimators {

    public static ValueAnimator forHeight(View view, int startValue, int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue,endValue);
        animator.addUpdateListener(updateListenerForHeight(view));
        return animator;
    }
    public static ValueAnimator forWidth(View view, int startValue, int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue,endValue);
        animator.addUpdateListener(updateListenerForWidth(view));
        return animator;
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PRIVATE @@@@@@@@@@@@@@@@@@@@@@@@@
    private static ValueAnimator.AnimatorUpdateListener updateListenerForHeight(View view) {
        return new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newValue = (int)valueAnimator.getAnimatedValue();
                view.getLayoutParams().height = newValue;
                view.requestLayout();
            }
        };
    }
    private static ValueAnimator.AnimatorUpdateListener updateListenerForWidth(View view) {
        return new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newValue = (int)valueAnimator.getAnimatedValue();
                view.getLayoutParams().width = newValue;
                view.requestLayout();
            }
        };
    }

}
