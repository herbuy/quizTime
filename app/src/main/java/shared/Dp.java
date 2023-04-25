package shared;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

public class Dp {


    private static Context context;

    public static void setContext(Context context) {
        Dp.context = context;
    }

    public static int scaleBy(float scaleFactor) {
        return (int)((defaultItemWidth() * scaleFactor ));

    }

    private static int defWidth = 0;
    private static int defaultItemWidth() {

        if(defWidth == 0){
            defWidth = (int)((0.025f * metrics().widthPixels));

        }
        return defWidth;

    }

    public static int screenWidthPixels(){
        return metrics().widthPixels;
    }

    //====================


    public static int normal(){
        return scaleBy(1f);

    }

    public static int half_em(){

        return scaleBy(0.5f);
    }




    public static int one_point_5_em() {
        return scaleBy(1.5f);
        //return (int)(normal() * 1.5f);
    }

    public static int two_em() {
        return scaleBy(2f);
    }
    public static int three_em() {
        return scaleBy(3f);
    }

    public static int ui_container() {
        return two_em();
    }

    public static int four_em() {
        return scaleBy(4);
    }

    public static int eight_em() {
        return scaleBy(8);
    }

    public static float pixels(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,value,metrics());
    }

    private static float mm(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM,value,metrics());
    }

    private static float inches(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN,value,metrics());
    }

    private static float dip(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics());
    }

    /** can be used to specify a literal value rather than relative */
    public static int pixels(int value) {
        return (int) pixels((float) value);
    }

    private static float textSizeNormal;

    public static float textSizeNormal() {
        computeIfNotComputed();
        return textSizeNormal;
    }

    private static void computeIfNotComputed() {

        if(textSizeNormal <= 0){

            float textSizeInPixels = 0.057f * metrics().widthPixels;

            textSizeNormal = DisplayUtil.px2sp(context,textSizeInPixels);
        }

    }

    public static float textSizeScaleby(float factor) {
        return textSizeNormal() * factor;
    }

    public static float textSizeHeader() {
        return textSizeScaleby(1.1f);
    }

    public static DisplayMetrics metrics() {
        return context.getResources().getDisplayMetrics();
    }

    public static void setTextSize(TextView textView) {
        setTextSize(textView, 1f);
    }
    /** size is based on the screen width */
    public static void setTextSize(TextView textView, float scaleFactor){
        float normalTextSizeInPx = 0.057f * metrics().widthPixels;

        textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                normalTextSizeInPx * scaleFactor
        );
    }


}

