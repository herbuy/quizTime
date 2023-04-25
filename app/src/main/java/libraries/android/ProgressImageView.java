package libraries.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;


@SuppressLint("AppCompatCustomView")
public abstract class ProgressImageView {

    private final Context context;
    private View frameLayout;

    public ProgressImageView(Context context) {
        this.context = context;
        frameLayout = createLayout(context);
        onCreate(this);
        setStateToInitial();

    }

    protected abstract void onCreate(ProgressImageView progressImageView);

    private View createLayout(Context context) {
        RelativeLayout layout = createContainer(context);
        layout.addView(button());
        layout.addView(progressBar());
        return layout;
    }

    private RelativeLayout createContainer(Context context) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
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
        imageView = onCreateImageView();
        imageView.setLayoutParams(buttonLayoutParams());
        imageView.setOnClickListener(defaultOnClickListener);
        return imageView;
    }

    protected abstract ImageView onCreateImageView();

    private ViewGroup.LayoutParams buttonLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        modifyImageViewLayoutParams(params);
        return params;
    }

    protected abstract void modifyImageViewLayoutParams(RelativeLayout.LayoutParams params);


    private ViewGroup.LayoutParams progressBarLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        modifyProgressBarLayoutParams(params);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    protected abstract void modifyProgressBarLayoutParams(RelativeLayout.LayoutParams params);

    public void setOnClickListener(@Nullable View.OnClickListener listener) {
        imageView.setOnClickListener(listener);
    }

    private View.OnClickListener defaultOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setStateToBusy();
        }
    };

    public final void setStateToInitial() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setClickable(true);
        onStateChangedToInitial(this);
    }

    protected abstract void onStateChangedToInitial(ProgressImageView progressImageView);

    public final void setStateToBusy() {
        progressBar.setVisibility(View.VISIBLE);
        if(showOnlyProgressBarIfBusy()){
            imageView.setVisibility(View.GONE);
        }
        else{
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setClickable(false);
    }

    public void setStateToSuccess() {
        setStateToSuccess(null);
    }


    public void setStateToSuccess(final Runnable afterResetUponSuccess) {

        imageView.setClickable(true);
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        onSuccess(this);
    }

    protected void onSuccess(ProgressImageView sender){};

    public void setStateToFailed() {
        imageView.setClickable(true);
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        onFail(this);

    }

    protected void onFail(ProgressImageView sender){}

    private boolean showOnlyProgressBarIfBusy() {
        return false;
    }
    private ImageView imageView;
    private ProgressBar progressBar;

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
    public View getView() {
        return frameLayout;
    }


    //================

}
