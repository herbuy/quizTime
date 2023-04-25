package utils;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Format {

    public static TextView SetText(TextView textView, CharSequence text) {
        textView.setText(text);
        return textView;
    }
    public static TextView SetHtml(TextView textView, String html) {
        textView.setText(Html.fromHtml(html));
        return textView;
    }

    public static class Font {

        public static <T extends TextView> T size(T textView, float size) {
            textView.setTextSize(size);
            return textView;
        }

        public static <T extends TextView> T color(T textView, int color) {
            textView.setTextColor(color);
            return textView;
        }

        public static <T extends TextView> T normal(T textView) {
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
            return textView;
        }

        public static <T extends TextView> T bold(T textView) {
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            return textView;
        }

        public static <T extends TextView> T italic(T textView) {
            textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            return textView;
        }

        public static <T extends TextView> T boldItalic(T textView) {
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD_ITALIC);
            return textView;
        }

    }

    public static class Alignment {

        public static <T extends TextView> T centerHorizontal(T textView) {
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            return textView;
        }

        public static <T extends LinearLayout> T centerHorizontal(T linearLayout) {
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            return linearLayout;
        }

        public static <T extends TextView> T centerVertical(T textView) {
            textView.setGravity(Gravity.CENTER_VERTICAL);
            return textView;
        }

        public static <T extends LinearLayout> T centerVertical(T linearLayout) {
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            return linearLayout;
        }

        public static <T extends TextView> T center(T textView) {
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            return textView;
        }

        public static <T extends LinearLayout> T center(T linearLayout) {
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            return linearLayout;
        }

        public static <T extends LinearLayout> T bottomRight(T linearLayout) {
            linearLayout.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            return linearLayout;
        }

        public static <T extends LinearLayout> T bottomLeft(T linearLayout) {
            linearLayout.setGravity(Gravity.BOTTOM | Gravity.LEFT);
            return linearLayout;
        }

        public static <T extends LinearLayout> T bottomCenter(T linearLayout) {
            linearLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            return linearLayout;
        }
    }

    public static class Padding {
        public static <T extends View> T all(T view, int value) {
            view.setPadding(value, value, value, value);
            return view;
        }

        public static <T extends View> T all(T view, int left, int top, int right, int bottom) {
            view.setPadding(left, top, right, bottom);
            return view;
        }
        public static <T extends View> T all(T view, int horizontal, int vertical) {
            view.setPadding(horizontal, vertical, horizontal, vertical);
            return view;
        }

        public static <T extends View> T left(T view, int value) {
            view.setPadding(value, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
            return view;
        }

        public static <T extends View> T top(T view, int value) {
            view.setPadding(view.getPaddingLeft(), value, view.getPaddingRight(), view.getPaddingBottom());
            return view;
        }

        public static <T extends View> T right(T view, int value) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), value, view.getPaddingBottom());
            return view;
        }

        public static <T extends View> T bottom(T view, int value) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), value);
            return view;
        }
    }

    public static class Background {
        public static <T extends View> T color(T view, int color) {
            view.setBackgroundColor(color);
            return view;
        }
        public static <T extends View> T drawable(T view, Drawable drawable) {
            view.setBackground(drawable);
            return view;
        }
    }


    public static class ChangeCase {

        public static <T extends TextView> T upper(T textView) {
            CharSequence text = textView.getText();
            if (text != null) {
                textView.setText(text.toString().toUpperCase());
            }
            return textView;
        }

        public static <T extends TextView> T toLower(T textView) {
            CharSequence text = textView.getText();
            if (text != null) {
                textView.setText(text.toString().toLowerCase());
            }
            return textView;
        }
    }

    public static class Heading {

    }

    public static class Bullets {

    }

    public static class Numbering {

    }

    public static class Picture {

    }

    public static class Charts {

    }

    public static class Shapes {

    }

    public static class Table {

    }

    public static class Figure {

    }

    public static class Replace {

    }

    public static class Header {

    }

    public static class Footer {

    }

    public static class Comment {

    }

    public static class Margins {

        public static <T extends View> T create(T view, int w, int h, int left, int top, int right, int bottom) {

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(w, h);
            marginLayoutParams.setMargins(left, top, right, bottom);
            view.setLayoutParams(marginLayoutParams);
            return view;
        }

        public static <T extends View> T matchWrap(T view, int left, int top, int right, int bottom) {
            return create(
                    view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    left, top, right, bottom
            );
        }
        public static <T extends View> T wrapWrap(T view, int left, int top, int right, int bottom) {
            return create(
                    view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    left, top, right, bottom
            );
        }
    }

    public static class SpellCheck {

    }

    public static class Translate {

    }

    public static class Sort {

    }

    public static class Borders {

    }

    public static class Count {
        //words, letters
    }

    public static class Split {

    }

    public static class CopyFormat {
        //can create style, apply style to an element, remove styles from element, set style for an element, clone/copy a style
    }

    public static class Wrap {

        public static LinearLayout linearLayoutVertical(View child) {
            LinearLayout layout = new LinearLayout(child.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(child);
            return layout;
        }
    }

    public static class LayoutParams {

        public static <T extends View> T widthHeight(T view, int width, int height) {
            view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            return view;
        }

        public static <T extends View> T wrapWrap(T view) {
            widthHeight(
                    view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            return view;
        }
        public static <T extends View> T matchMatch(T view) {
            widthHeight(
                    view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            return view;
        }

        public static <T extends View> T matchWrap(T view) {
            widthHeight(
                    view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            return view;
        }

        public static <T extends View> T wrapMatch(T view) {
            widthHeight(
                    view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            return view;
        }
    }
}
