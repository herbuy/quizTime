package libraries.android;

import android.graphics.drawable.GradientDrawable;

public class MakeALinearGradient {
    public static GradientDrawable where(int... colors) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setColors(colors);
        return drawable;
    }
}
