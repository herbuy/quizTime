package libraries.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatRatingBar;

public class HerbuyRatingBar extends AppCompatRatingBar {
    public HerbuyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HerbuyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HerbuyRatingBar(Context context) {
        super(context,null,android.R.attr.ratingBarStyleIndicator);
        init();
    }



    private void init() {
        setMax(5);
        setNumStars(5);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setEnabled(true);
        setIsIndicator(false);

    }
}
