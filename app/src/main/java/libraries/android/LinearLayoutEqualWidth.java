package libraries.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LinearLayoutEqualWidth extends LinearLayout {
    public LinearLayoutEqualWidth(Context context) {
        super(context);
        init();
    }



    public LinearLayoutEqualWidth(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearLayoutEqualWidth(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        this.setOrientation(HORIZONTAL);
    }

    public  void addView(View view){
        super.addView(view);
        view.setLayoutParams(itemLayoutParams(1,defaultSpacing()));
    }

    public LinearLayoutEqualWidth addView(View view, float weight){
        super.addView(view);
        view.setLayoutParams(itemLayoutParams(weight,defaultSpacing()));
        return this;
    }

    public LinearLayoutEqualWidth addView(View view, float weight, int itemSpacing){
        super.addView(view);
        view.setLayoutParams(itemLayoutParams(weight,itemSpacing));
        return this;
    }

    private int defaultSpacing() {
        return 16;
    }

    private ViewGroup.LayoutParams itemLayoutParams(float weight, int spacing) {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 0;
        params.weight = weight;
        if(getChildCount() > 1){
            params.leftMargin = spacing;
        }
        return params;
    }

    public LinearLayoutEqualWidth add(View... children) {
        for(View child : children){
            addView(child);
        }
        return this;
    }
}

