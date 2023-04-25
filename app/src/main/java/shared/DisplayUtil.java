package shared;


import android.content.Context;

public class DisplayUtil {
    /**
     * Convert the px value to dip or dp value to ensure that the size remains unchanged

     *

     * @param pxValue
     *
     * (Density attribute in DisplayMetrics class)

     * @return */public static int px2dip(Context context, float pxValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale + 0.5f);
    }


    /**
     * Convert dip or dp value to px value to ensure that the size remains the same

     *

     * @param dipValue
     *
     * (Density attribute in DisplayMetrics class)

     * @return */public static int dip2px(Context context, float dipValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * Convert px value to sp value to ensure that the text size remains unchanged

     *

     * @param pxValue
     *
     * (Attribute scaledDensity in DisplayMetrics class)

     * @return */public static int px2sp(Context context, float pxValue) {
        final float fontScale =context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale + 0.5f);
    }


    /**
     * Convert sp value to px value to ensure that the text size remains unchanged

     *

     * @param spValue

     * (Attribute scaledDensity in DisplayMetrics class)

     * @return */public static int sp2px(Context context, float spValue) {
        final float fontScale =context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}

