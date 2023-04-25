package shared;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import libraries.android.ColorCalc;
import libraries.android.HerbuyDevice;
import libraries.android.MakeALinearGradient;
import libraries.android.MakeARadialGradient;
import libraries.android.MakeStateListDrawable;

public class Backgrounds {



    public static Drawable clickableItem(){
        return MakeStateListDrawable.where(Color.TRANSPARENT,Color.GRAY,30,200);
    }

    public static Drawable primaryWithGradient() {
        GradientDrawable drawable = MakeARadialGradient.where(
                HerbuyDevice.getSmallerOfWidthOrHeight(),
                //ItemColor.pastel(),
                ColorCalc.mixColors(ItemColor.primary(),Color.WHITE,0.2f),
                ItemColor.primary()

                //ColorCalc.makeNeutralHalfSat(ItemColor.primary(),100)
        );
        return drawable;
    }


    public static Drawable primaryButton() {
        GradientDrawable gradient = MakeALinearGradient.where(
                ColorCalc.makeJewelry(ItemColor.primary(),100),
                ColorCalc.makeJewelry(ItemColor.primary(),100)
        );
        gradient.setCornerRadius(Dp.normal());
        gradient.setStroke(1,Color.TRANSPARENT);
        return gradient;
    }

    public static Drawable secondaryButton() {

        GradientDrawable gradient = MakeALinearGradient.where(
                Color.WHITE,
                Color.WHITE
        );
        gradient.setCornerRadius(Dp.normal());
        gradient.setStroke(1,ColorCalc.makeJewelry(ItemColor.primary(),100));
        return gradient;
    }
}
