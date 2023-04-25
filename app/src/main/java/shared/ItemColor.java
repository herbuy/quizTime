package shared;


import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.skyvolt.quiztime.R;

import libraries.android.ColorCalc;

public class ItemColor {
    static Context context;

    public static void setContext(Context context) {
        ItemColor.context = context;
    }

    public static int primary() {


        int color = getColor(R.color.colorPrimary);
        return color;
    }
    public static int accent() {
        return getColor(R.color.colorAccent);
    }


    public static int error() {
        return Color.parseColor("#FF4400");
    }

    public static int success() {
        return Color.parseColor("#E5F9ED");
    }

    private static int getColor(int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }

    public static int textPrimary() {
        return Color.BLACK;
        //return ColorCalc.mixColors(ItemColor.primary(),Color.BLACK,0.8f);
    }

    public static int textSecondary(){
        return ColorCalc.mixColors(textPrimary(),Color.WHITE,0.3f);
    }

    public static int chatBg() {

        return ColorCalc.makeCalmPlus150(ItemColor.primary());

    }


    public static int highlight() {
        return ColorCalc.mixColors(ItemColor.primary(),Color.TRANSPARENT,0.9f);
    }

    public static int hintTextColor() {
        return Color.parseColor("#66000000");
    }

    public static int inverseJewelry50() {
        return ColorCalc.makeJewelry(ColorCalc.addHue(180,ItemColor.primary()),50);
    }

    public static int earth() {
        return ColorCalc.makeEarth(primary(),100);
    }

    public static int pastel() {
        return ColorCalc.makePastel(primary(),100);
    }

    public static int neutral() {
        return ColorCalc.makeNeutral(primary(),100);
    }

    public static int chapMessage() {
        return ColorCalc.mixColors(
                ColorCalc.chapMessage(primary()),
                Color.parseColor("#ffffdd"),
                0.5f
        );
    }

    public static int contrastPrimary() {
        return ColorCalc.getColorWithTheBetterContrast(Color.WHITE,Color.BLACK, ItemColor.primary());
    }

    public static int jewelry() {
        return ColorCalc.makeJewelry(primary(),100);
    }

    public static int tripDetailsCategoriesSection() {
        return ColorCalc.mixColors(ItemColor.primary(),Color.WHITE,0.1f);
    }
}



