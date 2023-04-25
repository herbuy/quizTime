package libraries.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;

import java.util.ArrayList;
import java.util.List;

public abstract class HerbuyRatingBarWithTitle {

    private ViewGroup layout;
    public final Context context;
    final OnRatingChanged onRatingChanged = new OnRatingChanged();


    public HerbuyRatingBarWithTitle(Context context) {
        this.context = context;
        layout = createUI(this);
    }

    public View getView(){
        return layout;
    }


    private ViewGroup createUI(HerbuyRatingBarWithTitle mediator) {
        ViewGroup layout = CreateLayout.where(mediator.context);
        layout.addView(CreateTitle.where(mediator));
        layout.addView(CreateRatingBar.where(mediator));
        return layout;
    }

    static class CreateTitle{

        static TextView where(HerbuyRatingBarWithTitle mediator) {
            TextView title = mediator.createTextView(mediator.context);
            title.setText(mediator.ratingBarTitle());
            mediator.onRatingChanged.subscribe(new OnRatingChanged.Subscriber(){
                @Override
                public void onRatingChanged(float rating) {
                    title.setText("Rating: "+rating);
                }
            });
            mediator.afterCreateTitle(title);
            return title;
        }
    }

    protected TextView createTextView(Context context) {
        return new TextView(context);
    }

    protected void afterCreateTitle(TextView title) {

    }

    protected abstract String ratingBarTitle();

    private static class CreateLayout{

        static LinearLayout where(Context context) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            return layout;
        }
    }


    private static class OnRatingChanged{
        List<Subscriber> subscribers = new ArrayList<>();
        public synchronized void publish(float rating) {
            for(Subscriber subscriber : subscribers){
                subscriber.onRatingChanged(rating);
            }
        }

        public synchronized void subscribe(Subscriber subscriber) {
            if(subscriber != null){
                subscribers.add(subscriber);
            }
        }

        private interface Subscriber{
            void onRatingChanged(float rating);
        }
    }

    private static class CreateRatingBar{

        private static AppCompatRatingBar where(HerbuyRatingBarWithTitle mediator){
            AppCompatRatingBar ratingBar = new AppCompatRatingBar(mediator.context,null,android.R.attr.ratingBarStyleIndicator);
            ratingBar.setMax(5);
            ratingBar.setNumStars(5);
            ratingBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ratingBar.setEnabled(true);
            ratingBar.setIsIndicator(false);
            handleRatingChanged(mediator, ratingBar);
            mediator.modifyRatingBar(ratingBar);
            return ratingBar;
        }

        private static void handleRatingChanged(HerbuyRatingBarWithTitle mediator, AppCompatRatingBar ratingBar) {
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                    mediator.onRatingChanged.publish(rating);
                    mediator.onRatingChanged(rating);
                }
            });
        }
    }

    protected abstract void onRatingChanged(float rating);

    protected void modifyRatingBar(AppCompatRatingBar ratingBar) {

    }

}
