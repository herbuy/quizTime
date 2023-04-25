package libraries.android;

import android.graphics.drawable.GradientDrawable;

public class MakeARadialGradient {
    public static GradientDrawable where(float radius, int... colors) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable.setColors(colors);
        drawable.setGradientRadius(radius);
        return drawable;
    }
}
