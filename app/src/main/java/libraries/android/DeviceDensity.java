package libraries.android;

import android.content.Context;

public class DeviceDensity {
    
    public static String toString(Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;


        if(density <= 0.75f){
            return "ldpi";
        }
        if(density <= 1f){
            return "mdpi";
        }
        if(density <= 1.5f){
            return "hdpi";
        }
        if(density <= 2f){
            return "xhdpi";
        }
        if(density <= 3f){
            return "xxhdpi";
        }
        return "xxxhdpi";
    }

    public static boolean isLDPI(Context context){
        return toString(context).equalsIgnoreCase("ldpi");
    }
    public static boolean isMDPI(Context context){
        return toString(context).equalsIgnoreCase("mdpi");
    }
    public static boolean isHDPI(Context context){
        return toString(context).equalsIgnoreCase("hdpi");
    }
    public static boolean isXHDPI(Context context){
        return toString(context).equalsIgnoreCase("xhdpi");
    }
    public static boolean isXXHDPI(Context context){
        return toString(context).equalsIgnoreCase("xxhdpi");
    }
    public static boolean isXXXHDPI(Context context){
        return toString(context).equalsIgnoreCase("xxxhdpi");
    }
}
