package libraries.android;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

public class MakeStateListDrawable {

    public static StateListDrawable where(int defaultColor, int colorWhenSelected, int alphaPercent, int exitFadeDuration) {
        StateListDrawable res = new StateListDrawable();
        res.setExitFadeDuration(exitFadeDuration);
        if(alphaPercent < 100 && alphaPercent >= 0){
            res.setAlpha(alphaPercent);
        }


        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(colorWhenSelected));
        res.addState(new int[]{}, new ColorDrawable(defaultColor));
        return res;
    }

    public static StateListDrawable where(int defaultColor, int colorWhenSelected, int alphaPercent) {
        return where(defaultColor,colorWhenSelected,alphaPercent,400);
    }
    public static StateListDrawable where(int defaultColor, int colorWhenSelected){
        return where(defaultColor,colorWhenSelected,100);
    }
}
