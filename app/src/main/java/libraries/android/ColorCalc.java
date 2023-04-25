package libraries.android;

import android.graphics.Color;

import java.util.Random;

public class ColorCalc {
    public static int setBrightness(float brightness, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[2] = Math.min(1, Math.max(0, brightness));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static Integer setHue(float hue, Integer color) {

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = Math.min(360, Math.max(0, hue));
        return Color.HSVToColor(hsv);
    }

    public static int multiplyBrightnessBy(float brightness, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[2] = Math.min(1, Math.max(0, hsv[2] * brightness));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int setSaturation(float saturation, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[1] = Math.min(1, Math.max(0, saturation));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int multiplySaturationBy(float saturation, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[1] = hsv[1] * saturation;
        hsv[1] = Math.max(0, Math.min(1f, hsv[1]));
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int setAlpha(float newAlpha, int color) {
        return Color.argb((int) (newAlpha * 255), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int inverseColor(int color) {
        return Color.argb(
                Color.alpha(color),
                255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color)
        );
    }

    public static int addHue(int hueAmount, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        int originalAlpha = Color.alpha(color);

        hsv[0] = (hsv[0] + hueAmount) % 360;
        return Color.HSVToColor(originalAlpha, hsv);
    }

    public static int addHSV(float[] hsvToAdd, int color) {
        float[] hsvOrigColor = new float[3];
        Color.colorToHSV(color, hsvOrigColor);

        return Color.HSVToColor(
                Color.alpha(color),
                new float[]{
                        (hsvOrigColor[0] + hsvToAdd[0]),
                        hsvOrigColor[1] + hsvToAdd[1],
                        hsvOrigColor[2] + hsvToAdd[2]
                }
        );
    }

    public static int mixColors(int color1, int color2, float fractionColor2) {
        return Color.argb(
                (int) ((1f - fractionColor2) * Color.alpha(color1) + fractionColor2 * Color.alpha(color2)),
                (int) ((1f - fractionColor2) * Color.red(color1) + fractionColor2 * Color.red(color2)),
                (int) ((1f - fractionColor2) * Color.green(color1) + fractionColor2 * Color.red(color2)),
                (int) ((1f - fractionColor2) * Color.blue(color1) + fractionColor2 * Color.blue(color2))
        );
    }

    public static int mixColors(int color1, int color2) {
        return mixColors(color1, color2, 0.5f);
    }

    public static int mixColors(int color1, Integer[] argbToMix) {
        return Color.argb(
                (Color.alpha(color1) + argbToMix[0]) / 2,
                (Color.red(color1) + argbToMix[1]) / 2,
                (Color.green(color1) + argbToMix[2]) / 2,
                (Color.blue(color1) + argbToMix[3]) / 2
        );
    }

    public static int color2Mix(int startColor, int finalColor) {
        return Color.argb(
                2 * Color.alpha(finalColor) - Color.alpha(startColor),
                2 * Color.red(finalColor) - Color.red(startColor),
                2 * Color.green(finalColor) - Color.green(startColor),
                2 * Color.blue(finalColor) - Color.blue(startColor)
        );

    }

    public static Float[] getSaturationAndBrightnessFraction(int startColor, int finalColor){
        float[] hsvStartColor = new float[3];
        float[] hsvFinalColor = new float[3];

        Color.colorToHSV(startColor,hsvStartColor);
        Color.colorToHSV(finalColor,hsvFinalColor);
        return new Float[]{
                hsvFinalColor[1] / hsvStartColor[1],
                hsvFinalColor[2] / hsvStartColor[2]
        };

    }

    public static int multiplySatAndBrightBy(Float[] values, int color){
        return multiplyBrightnessBy(values[1],multiplySaturationBy(values[0],color));
    }

    /** returns the rgb values to mix in first color to get final color */
    public static Integer[] color2Mix2(int startColor, int finalColor) {
        return new Integer[]{
                2 * Color.alpha(finalColor) - Color.alpha(startColor),
                2 * Color.red(finalColor) - Color.red(startColor),
                2 * Color.green(finalColor) - Color.green(startColor),
                2 * Color.blue(finalColor) - Color.blue(startColor)
        };

    }

    private static String intToHex(int value){
        String hex = Integer.toHexString(value);
        if(hex.length() < 2){
            hex = "0"+hex;
        }
        return hex;
    }
    public static String toHex(int color) {
        return ""
                + intToHex(Color.alpha(color))
                + intToHex(Color.red(color))
                + intToHex(Color.green(color))
                + intToHex(Color.blue(color))
                ;
    }

    public static String toHexNoAlpha(int color) {
        return ""
                + intToHex(Color.red(color))
                + intToHex(Color.green(color))
                + intToHex(Color.blue(color))
                ;
    }

    public static float[] colorDifferenceHSV(int color1, int color2) {
        float[] hsvColor1 = new float[3];
        Color.colorToHSV(color1, hsvColor1);

        float[] hsvColor2 = new float[3];
        Color.colorToHSV(color2, hsvColor2);

        return new float[]{
                hsvColor2[0] - hsvColor1[0],
                hsvColor2[1] - hsvColor1[1],
                hsvColor2[2] - hsvColor1[2]
        };


    }

    public static float getBrightness(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[2];
    }

    public static float getSaturation(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[1];
    }
    public static float getHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[0];
    }

    public static int getAlpha(Integer color) {
        return Color.alpha(color);
    }

    public static int getRed(Integer color) {
        return Color.red(color);
    }

    public static int getGreen(Integer color) {
        return Color.green(color);
    }

    public static int getBlue(Integer color) {
        return Color.blue(color);
    }

    public static Integer getGrayscale(Integer color) {
        int channelValue =( (int)(0.30 * getRed(color)) + (int)(0.59 * getGreen(color)) + (int)(0.11 * getBlue(color)) ) / 3;
        return Color.argb(
                getAlpha(color),
                channelValue,
                channelValue,
                channelValue
        );
    }

    public static double getContrastRatio(int color1, int color2){
        double relativeLuminanceColor1 = getRelativeLuminance(color1);
        double relativeLuminanceColor2 = getRelativeLuminance(color2);
        return (Math.max(relativeLuminanceColor1,relativeLuminanceColor2) + 0.05)
                /
                (Math.min(relativeLuminanceColor1,relativeLuminanceColor2) + 0.05);
    }

    public static double getRelativeLuminance(Integer color){

        return (
                0.2126 * getAdjustedValue(getRed(color)) +
                        0.7152 * getAdjustedValue(getGreen(color)) +
                        0.0722 * getAdjustedValue(getBlue(color))
        );

    }

    private static double getAdjustedValue(int channelValue){
        double rawValue = channelValue / 255d;
        return  rawValue <= 0.03928 ? (rawValue / 12.92f) : Math.pow((rawValue+0.055)/1.055d,2.4d);
    }

    public static int makeColorFromRGB(int red, int green, int blue) {
        return Color.rgb(red,green,blue);
    }

    public static int makeColorFromHSB(float hue, float sat, float brightness) {
        return Color.HSVToColor(new float[]{hue,sat,brightness});
    }

    public static int makeEarth(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.36f, 0.41f, 0.36f, 0.76f,percentage);
    }

    public static int makeFlorescent(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.63f, 1f, 0.82f, 1f,percentage);
    }

    public static int makeJewelry(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.73f, 0.83f, 0.56f, 0.76f,percentage);
    }

    public static int makePastel(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.14f, 0.21f, 0.89f, 0.96f,percentage);
    }
    public static int makeChapSap(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.5f, 0.93f, 0.30f, 0.37f,percentage);
    }

    public static int makeNeutral(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.01f, 0.1f, 0.70f, 0.99f,percentage);
    }

    private static int getEffectiveColor(int baseColor, float minS, float maxS, float minV, float maxV, int percentage) {
        float[] hsv = new float[3];
        Color.colorToHSV(baseColor,hsv);
        hsv[1] = getEffectiveValue(minS,maxS,percentage);
        hsv[2] = getEffectiveValue(minV,maxV,percentage);
        return Color.HSVToColor(hsv);
    }


    private static float randomFractionBetween(float min, float max) {
        return ((Math.abs(new Random().nextInt()) % 101) / 100f) * (max - min) + min;
    }

    private static float getEffectiveValue(float min, float max, int percentage) {
        return percentage / 100f * (max - min) + min;
    }


    public static int getColorWithTheBetterContrast(int color1, int color2, int comparedToColor) {
        double cont1 = ColorCalc.getContrastRatio(color1,comparedToColor);
        double cont2 = ColorCalc.getContrastRatio(color2,comparedToColor);
        return cont1 > cont2 || cont1 > 2 ? color1 : color2;
    }


    public static Integer getRandomColorWithGoodContrastWith(double minContrastRatio, int maxAttempts, int... colors) {

        double previousContrastRatio = 0;
        int bestColorFound = 0;
        for(int attemptsSofar = 0; attemptsSofar < maxAttempts;attemptsSofar++){

            int color = getRandomColor();
            double newContrastRatio = getHighestContrastPossible(color,colors);
            if(newContrastRatio >= minContrastRatio){
                return color;
            }
            else if(newContrastRatio > previousContrastRatio){
                bestColorFound = color;
                previousContrastRatio = newContrastRatio;
            }

        }

        return bestColorFound;
    }

    private static double getHighestContrastPossible(int color, int[] colors) {
        double highestContrast = 0;
        for(int index = 0; index < colors.length;index++){
            double newContrastRatio = getContrastRatio(color,colors[index]);
            if(newContrastRatio > highestContrast){
                highestContrast = newContrastRatio;
            }
        }
        return highestContrast;
    }

    public static Integer getRandomColorWithGoodContrastWith(int color2, double minContrastRatio, int maxAttempts) {

        double previousContrastRatio = 0;
        int bestColorFound = 0;
        for(int attemptsSofar = 0; attemptsSofar < maxAttempts;attemptsSofar++){

            int color = getRandomColor();
            double newContrastRatio = getContrastRatio(color,color2);
            if(newContrastRatio >= minContrastRatio){
                return color;
            }
            else if(newContrastRatio > previousContrastRatio){
                bestColorFound = color;
                previousContrastRatio = newContrastRatio;
            }

        }

        return bestColorFound;
    }

    public static int getRandomColor() {
        return Color.rgb(
                randomRGBChannelValue(),
                randomRGBChannelValue(),
                randomRGBChannelValue()
        );
    }

    private static int randomRGBChannelValue() {
        return Math.abs(new Random().nextInt()) % 256;
    }

    public static int modifyHSL(int baseColor, int addHue, float multiplySatBy, float multiplyBrightnessBy) {
        return multiplyBrightnessBy(multiplyBrightnessBy,multiplySaturationBy(multiplySatBy,addHue(addHue,baseColor)));
    }

    public static int setAlpha(int baseColor, float amount) {
        return Color.argb(
                (int)(amount * 255),
                Color.red(baseColor),
                Color.green(baseColor),
                Color.blue(baseColor)
        );
    }
    public static int multiplyAlpha(int baseColor, float amount) {
        return Color.argb(
                (int)(Color.alpha(baseColor) * amount),
                Color.red(baseColor),
                Color.green(baseColor),
                Color.blue(baseColor)
        );
    }

    /** returns white if the color you pass is closer to black, else black*/
    public static int whiteOrBlack(int inputColor) {
        if(getContrastRatio(Color.WHITE,inputColor) > getContrastRatio(Color.BLACK,inputColor)){
            return Color.WHITE;
        }
        else{
            return Color.BLACK;
        }

    }

    public static int getBlackOrWhite(int inputColor) {
        return whiteOrBlack(inputColor);

    }

    public static int makeNeutralHalfSat(int baseColor, int percentage){
        return getEffectiveColor(baseColor, 0.01f, 0.02f, 0.70f, 0.99f,percentage);
    }

    public static int makeCalmBg(int baseColor) {
        return setSaturation(0.07f,
                setBrightness(0.93f, baseColor)
        );
    }

    public static int makeCalmPlus30(int baseColor) {
        return makeCalmBg(addHue(30,baseColor));
    }

    public static int makeCalmPlus60(int baseColor) {
        return makeCalmBg(addHue(60,baseColor));
    }

    public static int makeCalmPlus90(int baseColor) {
        return makeCalmBg(addHue(90,baseColor));
    }

    public static int makeCalmPlus120(int baseColor) {
        return makeCalmBg(addHue(120,baseColor));
    }
    public static int makeCalmPlus150(int baseColor) {
        return makeCalmBg(addHue(150,baseColor));
    }
    public static int makeCalmPlus180(int baseColor) {
        return makeCalmBg(addHue(180,baseColor));
    }

    public static int chapMessage(int baseColor){
        return getEffectiveColor(baseColor, 0.22f, 0.22f, 1.0f, 1.0f,100);
    }

}
