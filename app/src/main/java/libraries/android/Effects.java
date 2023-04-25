package libraries.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/** this simple library allows you to give feedback to the user
 * USAGE
 * indicate.clicked()
 * */
public abstract class Effects {


    public static abstract class Effect{
        private long delay;
        private long duration = 100;

        public final Effect delay(long delay){
            this.delay = delay;
            return this;
        }

        public final Effect duration(long duration){
            this.duration = duration;
            return this;
        }

        public final void onComplete(Runnable runnable){
            if(runnable == null){
                runnable = defaultForOnComplete();
            }
            play(runnable,delay,duration);
        }

        private Runnable defaultForOnComplete() {
            //so that we always pass a runnable that can be called, even if it does nothing
            //simplifies the code for the subclasses so they dont need to check if runnable is null.
            return new Runnable() {
                @Override
                public void run() {

                }
            };
        }

        protected abstract void play(Runnable onComplete, long delay, long duration);
    }

    //------------------------------ shrink
    public static class Shrink extends Effect{
        private View view;

        public Shrink(View view) {
            this.view = view;
        }

        @Override
        protected void play(Runnable onComplete, long delay, long duration) {

            view.animate().scaleX(0.5f).scaleY(0.5f).setStartDelay(delay).setDuration(duration).withEndAction(new Runnable() {
                @Override
                public void run() {
                    onComplete.run();
                }
            });
        }
    }

    public static class Expand extends Effect{
        private View view;

        public Expand(View view) {
            this.view = view;
        }

        @Override
        protected void play(Runnable onComplete, long delay, long duration) {

            view.animate().scaleX(1.2f).scaleY(1.2f).setStartDelay(delay).setDuration(duration).withEndAction(new Runnable() {
                @Override
                public void run() {
                    onComplete.run();
                }
            });
        }
    }

    //------------------------------ shrink then back
    public static class ShrinkThenBack extends Effect{
        private View view;

        public ShrinkThenBack(View view) {
            this.view = view;
        }

        @Override
        protected void play(Runnable onComplete, long delay, long duration) {

            view.animate().scaleX(0.5f).scaleY(0.5f).setStartDelay(delay).setDuration(duration).withEndAction(new Runnable() {
                @Override
                public void run() {
                    view.animate().scaleX(1).scaleY(1).setDuration(100).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            onComplete.run();
                        }
                    });
                }
            });
        }
    }

    //------------------------------ swell then back. Can be good for acknowledging clicks.
    public static class SwellThenBack extends Effect{
        private View view;

        public SwellThenBack(View view) {
            this.view = view;
        }


        @Override
        protected void play(Runnable onComplete, long delay, long duration) {

            view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(duration).setStartDelay(delay).withEndAction(new Runnable() {
                @Override
                public void run() {
                    view.animate().scaleX(1).scaleY(1).setDuration(100).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            onComplete.run();
                        }
                    });
                }
            });
        }
    }

    //------------------------------ typewriter. Can be good for displaying items
    public static class TypeWriter extends Effect{
        private View view;

        public TypeWriter(View view) {
            this.view = view;
        }

        public TypeWriter(TextView view) {
            this.view = view;
        }

        public TypeWriter(ViewGroup view) {
            this.view = view;
        }

        @Override
        protected void play(Runnable onComplete, long delay, long duration) {
            if(view instanceof ViewGroup){
                typeWriteViewGroupItems(onComplete,delay,duration);
            }
            else if(view instanceof TextView){
                typeWriteTextViewText(onComplete,delay,duration);
            }
            else{
                onComplete.run();
            }


        }

        private void typeWriteTextViewText(Runnable onComplete, long delay, long duration) {

            TextView textView = (TextView)view;
            String text = textView.getText() == null ? "": textView.getText().toString();
            int length = text.length();
            ValueAnimator animator = ValueAnimator.ofInt(1,length);
            animator.setStartDelay(delay);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int newValue = (int)valueAnimator.getAnimatedValue();
                    textView.setText(text.substring(0,newValue));
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onComplete.run();
                }
            });
            animator.start();

        }

        private void typeWriteViewGroupItems(Runnable onComplete, long delay, long duration) {
            ViewGroup viewGroup = (ViewGroup) view;
            hideAllChildren(viewGroup);
            showOneByOne(onComplete, viewGroup,delay,duration);

        }

        private void showOneByOne(Runnable onComplete, ViewGroup viewGroup, long delay, long duration) {
            ValueAnimator animator = ValueAnimator.ofInt(0,viewGroup.getChildCount() - 1);
            animator.setStartDelay(delay);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int itemIndexToShow = (int)valueAnimator.getAnimatedValue();
                    viewGroup.getChildAt(itemIndexToShow).setAlpha(1);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onComplete.run();
                }
            });
            animator.start();
        }

        private void hideAllChildren(ViewGroup viewGroup) {
            for(int itemIndex = 0; itemIndex < viewGroup.getChildCount();itemIndex++){
                viewGroup.getChildAt(itemIndex).setAlpha(0);
            }
        }
    }
}
