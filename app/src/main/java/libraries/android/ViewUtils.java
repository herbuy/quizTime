package libraries.android;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class ViewUtils {

    private static int getRequiredScrollY(View item, View itemList, View scrollView){

        if(itemTopEdge(item, itemList, scrollView) <= scrollView.getPaddingTop()){
            return (int)itemTopEdge(item, itemList, scrollView) - scrollView.getPaddingTop();
        }
        else if(itemBottomEdge(item, itemList,scrollView) > scrollView.getHeight() - scrollView.getPaddingBottom()){
            return (int)(itemBottomEdge(item, itemList,scrollView) - scrollView.getHeight() + scrollView.getPaddingBottom());
        }
        return 0;

    }

    private static float itemBottomEdge(View item, View itemList, View scrollView) {
        return itemTopEdge(item, itemList,scrollView) + item.getHeight();
    }

    private static float itemTopEdge(View item, View itemList, View scrollView) {
        return scrollView.getPaddingTop() + itemList.getY() + item.getY();
    }

    private static int getRequiredScrollX(View item, View itemList, View scrollView){

        if(itemLeftEdge(item, itemList, scrollView) < scrollView.getPaddingLeft()){
            return (int)itemLeftEdge(item, itemList, scrollView) - scrollView.getPaddingLeft();
        }
        else if(itemRightEdge(item, itemList, scrollView) > scrollView.getWidth() - scrollView.getPaddingRight()){
            return (int)(itemRightEdge(item,itemList, scrollView) - scrollView.getWidth() + scrollView.getPaddingRight());
        }
        return 0;
    }

    private static float itemLeftEdge(View item, View itemList, View scrollView) {
        return scrollView.getPaddingLeft() + itemList.getX() + item.getX() ;
    }

    private static float itemRightEdge(View item, View itemList, View scrollView) {
        return itemLeftEdge(item,itemList, scrollView) + item.getWidth();
    }

    public static void scrollToItem2(final View scrollView, View viewGroup, View itemView, int duration, int delay){

        viewGroup.post(new Runnable() {
            @Override
            public void run() {
                animateToItemX(itemView,viewGroup,scrollView,duration,delay);
                animateToItemY(itemView, viewGroup, scrollView, duration, delay);

            }
        });

    }

    private static void animateToItemX(View itemView, View viewGroup, View scrollView, int duration, int delay) {
        int requiredScrollX = ViewUtils.getRequiredScrollX(

                itemView,
                viewGroup,
                scrollView
        );

        ValueAnimator valueAnimator = ValueAnimator.ofInt(
                scrollView.getScrollX(),
                requiredScrollX
        );

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newVal = (int) animation.getAnimatedValue();
                scrollView.setScrollX(newVal);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(delay);
        valueAnimator.start();
    }

    private static void animateToItemY(View itemView, View viewGroup, View scrollView, int duration, int delay) {
        int requiredScrollY = ViewUtils.getRequiredScrollY(

                itemView,
                viewGroup,
                scrollView
        );

        ValueAnimator valueAnimator = ValueAnimator.ofInt(
                scrollView.getScrollY(),
                requiredScrollY
        );

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newVal = (int) animation.getAnimatedValue();
                scrollView.setScrollY(newVal);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(delay);
        valueAnimator.start();
    }

    public static void scrollToItem(final View scrollView, View itemView, int duration, int delay){


        /*
        ValueAnimator scrollXAnimator = ValueAnimator.ofFloat(scrollView.getScrollX(), itemView.getX());
        scrollXAnimator.setDuration(duration);
        scrollXAnimator.setStartDelay(delay);

        scrollXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();
                scrollView.setScrollX((int) newValue);
            }
        });*/

        ValueAnimator scrollYAnimator = ValueAnimator.ofFloat(scrollView.getScrollY(), itemView.getScrollY());
        scrollYAnimator.setDuration(duration);
        scrollYAnimator.setStartDelay(delay);

        scrollYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                scrollView.setScrollY((int) newValue);
            }
        });

        //scrollXAnimator.start();
        scrollYAnimator.start();

        /*
        final int totalSteps = 12;//fps
        final int length = 400;//millis
        final Handler handler = new Handler();

        if (totalSteps < 1) {
            return;
        }

        int periodBetweenSteps = length / totalSteps;
        final Timer timer = new Timer();

        if (beforeStart != null) {
            beforeStart.run();
        }

        timer.scheduleAtFixedRate(new TimerTask() {

            int currentStep = 0;

            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!exceedsTotalSteps()) {

                            final float fraction = (float) currentStep / (float) totalSteps;
                            //apply




                            currentStep += 1;

                            if (exceedsTotalSteps()) {
                                endAnimation();
                            }

                        }
                    }
                });
            }

            private boolean exceedsTotalSteps() {
                return currentStep > totalSteps;
            }

            private void endAnimation() {
                timer.cancel();

                if (onFinish != null) {
                    onFinish.onFinish(HerbuyAnimation.this);

                }
            }
        }, delay, periodBetweenSteps);*/

    }

    public static boolean hasChildren(ViewGroup viewGroup) {
        return viewGroup != null && viewGroup.getChildCount() > 0;
    }

    public static View lastChild(ViewGroup viewGroup) {
        return viewGroup.getChildAt(viewGroup.getChildCount() - 1);
    }

    public static void scrollToBottom(final ViewGroup viewGroup, final ScrollView scrollView) {

        //wait for the viewGroup to be ready. Remove the runnable if the change does not work
        viewGroup.post(new Runnable() {
            @Override
            public void run() {
                if (ViewUtils.hasChildren(viewGroup)) {

                    int requiredScrollY = ViewUtils.getRequiredScrollY(

                            ViewUtils.lastChild(viewGroup),
                            viewGroup,
                            scrollView
                    );

                    //MessageBox.show("scroll needed",context);
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(
                            scrollView.getScrollY(),
                            requiredScrollY
                            //(int)viewGroup.lastChild().getY()
                            //90000
                    );

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int newVal = (int) animation.getAnimatedValue();
                            scrollView.setScrollY(newVal);
                        }
                    });
                    valueAnimator.setDuration(400);
                    valueAnimator.start();

                }
            }
        });

    }
}
