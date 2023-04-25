package libraries.android;

import android.view.ViewGroup;

public class MarginParams {


    public static ViewGroup.MarginLayoutParams matchWrap(int left, int top, int right, int bottom) {

        return create(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                left,
                top,
                right,
                bottom
        );
    }

    public static ViewGroup.MarginLayoutParams matchMatch(int left, int top, int right, int bottom) {

        return create(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                left,
                top,
                right,
                bottom
        );
    }

    public static ViewGroup.MarginLayoutParams wrapWrap(int left, int top, int right, int bottom) {

        return create(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                left,
                top,
                right,
                bottom
        );
    }

    public static ViewGroup.MarginLayoutParams wrapMatch(int left, int top, int right, int bottom) {

        return create(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                left,
                top,
                right,
                bottom
        );
    }

    private static ViewGroup.MarginLayoutParams create(int w, int h, int left, int top, int right, int bottom) {

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(w, h);
        marginLayoutParams.setMargins(left, top, right, bottom);
        return marginLayoutParams;
    }

}
