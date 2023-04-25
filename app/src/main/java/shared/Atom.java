package shared;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import libraries.android.MakeDummy;
import libraries.android.ProgressButton;
import libraries.android.TextChangedListener;
import libraries.android.ColorCalc;

public class Atom {

    public static Button button(Context context, String text, View.OnClickListener onClickListener){
        Button button = new Button(context);
        button.setText(Html.fromHtml(text));
        button.setBackground(Backgrounds.primaryButton());
        button.setPadding(Dp.normal(),0,Dp.normal(),0);
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(onClickListener);
        Dp.setTextSize(button,0.75f);
        setTypeFace(button,Typeface.NORMAL);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)(Dp.normal() * 4.5)
        ));
        return button;
    }

    public static TextView buttonTextView(Context context, String text, View.OnClickListener onClickListener){
        text = text.toUpperCase();
        TextView btn = Atom.textView(context,text);
        btn.setClickable(true);
        btn.setBackground(Backgrounds.primaryButton());
        btn.setTextColor(Color.WHITE);
        btn.setPadding(Dp.one_point_5_em(),Dp.scaleBy(0.7f),Dp.one_point_5_em(),Dp.scaleBy(0.7f));
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        Dp.setTextSize(btn,0.75f);
        setTypeFace(btn,Typeface.NORMAL);
        btn.setOnClickListener(onClickListener);
        return btn;
    }


    public static TextView textView(Context context, String text, float scaleFactor) {
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        Dp.setTextSize(textView,scaleFactor);
        setTypeFace(textView,Typeface.NORMAL);
        return textView;
    }
    public static TextView textView(Context context, String text){
        return textView(context,text,1);
    }
    private static TextView textViewPrimary(Context context, String text, int typefaceStyle){
        text = text == null ? "" : text;
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        textView.setTextColor(ItemColor.textPrimary());
        textView.setTypeface(null,typefaceStyle);

        setTypeFace(textView,typefaceStyle);
        Dp.setTextSize(textView);
        return textView;
    }

    private static void setTypeFace(TextView textView,int typefaceStyle) {
        //textView.setTypeface(ResourcesCompat.getFont(textView.getContext(),R.font.yantramanav_regular),typefaceStyle);
    }

    public static TextView textViewPrimaryBold(Context context, String text){
        TextView textView = textViewPrimary(context,text,Typeface.BOLD);
        //textView.setTypeface(ResourcesCompat.getFont(textView.getContext(),R.font.yantramanav_regular),textView.getTypeface().getStyle());
        return textView;
    }
    public static TextView textViewPrimaryNormal(Context context, String text){
        return textViewPrimary(context, text,Typeface.NORMAL);
    }

    public static TextView textViewSecondary(Context context, String text){
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        Dp.setTextSize(textView,0.95f);
        textView.setTextColor(ItemColor.textSecondary());
        setTypeFace(textView,Typeface.NORMAL);
        return textView;
    }


    public static EditText editText(Context context, String hint,String text, int inputType, TextChangedListener textChangedListener) {
        if(textChangedListener == null){
            textChangedListener = emptyTextChangedListener();
        }

        final EditText editText = new EditText(context);
        editText.setHint(Html.fromHtml(hint));
        if(text != null && !text.trim().equalsIgnoreCase("")){
            editText.setText(text);
        }
        editText.setInputType(inputType);
        editText.addTextChangedListener(textChangedListener);


        Dp.setTextSize(editText);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Dp.scaleBy(5)
        ));
        editText.setHintTextColor(ItemColor.hintTextColor());
        setTypeFace(editText,Typeface.NORMAL);
        return editText;
    }

    public static EditText editText(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text,InputType.TYPE_CLASS_TEXT,textChangedListener);
    }


    public static EditText editProperNoun(Context context, String hint,String text, TextChangedListener textChangedListener) {
        return editText(context,hint,text, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_TEXT_FLAG_CAP_WORDS,textChangedListener);
    }


    public static EditText editText(Context context, String hint, TextChangedListener textChangedListener) {
        if(textChangedListener == null){
            textChangedListener = emptyTextChangedListener();
        }
        return editText(context,hint,"",textChangedListener);
    }

    public static TextChangedListener emptyTextChangedListener() {
        TextChangedListener textChangedListener;
        textChangedListener = new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textChangedListener;
    }

    public static EditText password(Context context, String hint,String text, TextChangedListener textChangedListener){
        EditText pass = editText(context,hint,text,InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,textChangedListener);
        return pass;
    }

    public static EditText password(Context context, String hint, TextChangedListener textChangedListener){
        return password(context,hint,"",textChangedListener);
    }

    private static View circle(Context context, View child) {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(Dp.four_em(), Dp.four_em()));
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        if(child != null){
            layout.addView(child);
        }
        return layout;
    }



    public static View lightBackground(View content) {
        LinearLayout linearLayout = MakeDummy.linearLayoutVertical(content.getContext());
        linearLayout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        linearLayout.setBackgroundColor(ColorCalc.mixColors(
                ItemColor.primary(),
                Color.WHITE,
                0.9f
        ));
        linearLayout.addView(content);
        return linearLayout;
    }

    public static View centeredText(Context context, String text) {
        return MakeDummy.padding(
                MakeDummy.centerHorizontal(
                        Atom.textView(context, text)
                ),
                Dp.scaleBy(4)
        );
    }

    public static View progressBar(Context context) {
        ProgressBar progressBar = new ProgressBar(context);
        return progressBar;
    }

    public static ProgressButton progressButton(Context context) {
        final libraries.android.ProgressButton button = new libraries.android.ProgressButton(context);
        button.getButton().setBackground(Backgrounds.primaryButton());
        button.getButton().setTextColor(Color.WHITE);
        button.getButton().setPadding(Dp.two_em(),0,Dp.two_em(),0);
        Dp.setTextSize(button.getButton(),0.75f);
        setTypeFace(button.getButton(),Typeface.NORMAL);
        button.getButton().post(new Runnable() {
            @Override
            public void run() {
                button.getButton().getLayoutParams().height = Dp.scaleBy(5);
                button.getButton().requestLayout();
            }
        });
        return button;
    }

    public static View snackbar(Context context,String text) {
        TextView snackbar = Atom.textView(context,text);
        snackbar.setTextColor(Color.WHITE);
        snackbar.setBackgroundColor(Color.parseColor("#444444"));
        snackbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        snackbar.setPadding(Dp.normal(), Dp.two_em(), Dp.normal(), Dp.two_em());
        snackbar.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        return snackbar;
    }

}

