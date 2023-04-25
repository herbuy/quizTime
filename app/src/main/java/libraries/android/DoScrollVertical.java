package libraries.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class DoScrollVertical {


    private static Handler handler = new Handler();


    public static void toItem(View item, ViewGroup layout, ScrollView scrollView, int scrollDuration, final Runnable onEndOrNull) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(scrollDuration <= 0){
                    instantScroll(item,layout,scrollView);
                }
                else{
                    smoothScroll(item, layout,scrollView,scrollDuration,onEndOrNull);
                }

            }
        });

    }



    private static void instantScroll(View item, ViewGroup layout, ScrollView scrollView) {
        scrollView.setScrollY(computeScrollY(item,layout,scrollView));
    }

    private static void smoothScroll(View item, ViewGroup layout, final ScrollView scrollView, int scrollDuration, final Runnable onEndOrNull) {
        ValueAnimator scrollYAnimator = ValueAnimator.ofFloat(scrollView.getScrollY(), computeScrollY(item,layout,scrollView));
        scrollYAnimator.setDuration(scrollDuration);
        scrollYAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(onEndOrNull != null){
                    onEndOrNull.run();
                }
            }
        });
        scrollYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                scrollView.setScrollY((int) newValue);
            }
        });
        scrollYAnimator.start();
    }

    public static void toItem(int position, ViewGroup layout, ScrollView scrollView, int scrollDuration, final Runnable onEndOrNull){
        if(position < 0){
            toTop(layout,scrollView,scrollDuration,onEndOrNull);
        }
        else if(position >= layout.getChildCount()){
            toBottom(layout,scrollView,scrollDuration,onEndOrNull);
        }

        else{
            toItem(layout.getChildAt(position),layout,scrollView,scrollDuration,onEndOrNull);
        }

    }

    public static void toBottom(ViewGroup layout, ScrollView scrollView, int scrollDuration, final Runnable onEndOrNull) {
        toItem(getLastItem(layout),layout,scrollView,scrollDuration,onEndOrNull);
    }

    public static void toTop(ViewGroup layout, ScrollView scrollView, int scrollDuration, final Runnable onEndOrNull) {
        toItem(getFirstItem(layout),layout,scrollView,scrollDuration,onEndOrNull);
    }

    private static View getFirstItem(ViewGroup layout) {
        return layout.getChildAt(0);
    }

    private static View getLastItem(ViewGroup layout) {
        return layout.getChildAt(layout.getChildCount() - 1);
    }

    private static int computeScrollY(View item, ViewGroup layout, ScrollView scrollView) {
        //return itemBottomEdge(item,layout,scrollView) - scrollView.getHeight();

        if(itemBottomEdge(item,layout,scrollView) >= scrollView.getHeight()){
            return itemBottomEdge(item,layout,scrollView) - scrollView.getHeight();
        }
        else {
            return itemTopEdge(item,layout,scrollView);
        }

    }

    private static int itemTopEdge(View item, ViewGroup layout, ScrollView scrollView) {
        return (int)(layout.getY() + item.getY());
    }
    private static int itemBottomEdge(View item, ViewGroup layout, ScrollView scrollView) {
        return (int)(layout.getY() + item.getY() + item.getHeight());
    }
}
