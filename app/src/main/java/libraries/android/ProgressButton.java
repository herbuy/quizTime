package libraries.android;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;


@SuppressLint("AppCompatCustomView")
public class ProgressButton {
    private final Context context;
    private View frameLayout;
    public ProgressButton(Context context) {
        this.context = context;
        frameLayout = createLayout(context);
        setStateToInitial();
    }

    private View createLayout(Context context) {
        RelativeLayout layout = createContainer(context);
        layout.addView(button());
        layout.addView(progressBar());
        return layout;
    }

    private RelativeLayout createContainer(Context context) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }

    private View progressBar() {
        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(progressBarLayoutParams());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setElevation(10);
        }
        return progressBar;
    }

    private View button() {
        button = new Button(context);
        button.setLayoutParams(buttonLayoutParams());
        button.setOnClickListener(defaultOnClickListener);
        onConfigureButton(button);
        return button;
    }

    private ViewGroup.LayoutParams buttonLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }


    private ViewGroup.LayoutParams progressBarLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    public void setOnClickListener(@Nullable View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    protected void onConfigureButton(Button button) {

    }

    private View.OnClickListener defaultOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setStateToBusy();
        }
    };

    public final void setStateToInitial() {
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        button.setClickable(true);
        button.setText(getInitialText());
    }

    public final void setStateToBusy() {
        progressBar.setVisibility(View.VISIBLE);
        if(showOnlyProgressBarIfBusy()){
            button.setVisibility(View.GONE);
        }
        else{
            button.setVisibility(View.VISIBLE);
        }
        button.setClickable(false);
        button.setText(getTextWhenBusy());

        onBusy(this);
    }

    protected void onBusy(ProgressButton progressButton) {

    }


    public void setStateToSuccess() {
        setStateToSuccess(null);
    }

    private boolean returnToInitialState = true;

    public void setReturnToInitialStateOff() {
        this.returnToInitialState = false;
    }

    public void setStateToSuccess(final Runnable afterResetUponSuccess) {

        button.setClickable(true);
        button.setVisibility(View.VISIBLE);
        button.setText(textWhenSuccessful);
        progressBar.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(afterResetUponSuccess != null){
                    afterResetUponSuccess.run();
                }
                if(returnToInitialState){
                    setStateToInitial();
                }

            }
        }, timeBeforeResetOnSuccess());
    }

    protected long timeBeforeResetOnSuccess() {
        return 1000;
    }

    public void setStateToFailed() {
        button.setClickable(true);
        button.setVisibility(View.VISIBLE);
        button.setText(textOnError());
        progressBar.setVisibility(View.GONE);
    }

    protected String textOnError() {
        return "Try again";
    }

    private String textWhenSuccessful = "Done";

    public void setTextWhenSuccessful(String textWhenSuccessful) {
        this.textWhenSuccessful = textWhenSuccessful;
    }

    private boolean showOnlyProgressBarIfBusy() {
        return false;
    }

    private Button button;
    private ProgressBar progressBar;

    public Button getButton() {
        return button;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    private String initialText = "Button";
    public void setInitialText(String initialText) {
        this.initialText = initialText;
        getButton().setText(initialText);
    }

    protected String getInitialText() {
        return initialText;
    }

    private String textWhenBusy = "Wait";
    protected String getTextWhenBusy() {
        return textWhenBusy;
    }

    public void setTextWhenBusy(String textWhenBusy) {
        this.textWhenBusy = textWhenBusy;
    }

    public View getView() {
        return frameLayout;
    }


    //================

}

