
package libraries.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public abstract class HorizontalList<DataType> {

    private final Context context;
    private HorizontalScrollView scrollView;

    public HorizontalList(Context context) {
        this.context = context;
        scrollView = new HorizontalScrollView(context);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.addView(tableLayout());
    }


    private View tableLayout() {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.addView(tableRow());
        return tableLayout;
    }

    private View tableRow() {
        TableRow tableRow = new TableRow(context);
        tableRow.addView(horizontalLinearLayout());
        return tableRow;
    }

    private LinearLayout horizontalLinearLayout;
    private View horizontalLinearLayout() {
        horizontalLinearLayout = new LinearLayout(context);
        horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return horizontalLinearLayout;
    }

    public final View getView(){
        return scrollView;
    }

    //------------
    private List<DataType> listItems = new ArrayList<>();

    public List<DataType> getList(){
        return listItems;
    }

    public final void add(final DataType itemData){
        if(itemData == null){
            return;
        }
        listItems.add(itemData);
        final View itemView = createItemView(itemData);
        addToVisibleList(itemView);
        handleItemClick(itemData, itemView);
        makeItemVisible(itemView);
        onListChanged(listItems);
    }

    public final void addAll(List<DataType> items){
        if(items == null){
            return;
        }
        for(DataType item: items){
            add(item);
        }
    }

    private void addToVisibleList(View itemView) {
        ensureInvisbleOnDisplay(itemView);
        if(listEmpty()){
            animateAddItem(itemView);

        }
        else{
            justAddItem(itemView);
        }
    }

    private void justAddItem(View itemView) {
        horizontalLinearLayout.addView(itemView);
    }

    private void animateAddItem(View itemView) {
        hideTheItem(itemView);
        Sdk.beginDelayedTransition(horizontalLinearLayout);
        horizontalLinearLayout.addView(itemView);
        showTheItem(itemView);
    }

    private void ensureInvisbleOnDisplay(View itemView) {
        scaleToZeroSize(itemView);//such that when displayed, it appears and invisible but retains its space
    }

    private void scaleToZeroSize(View itemView) {
        itemView.setScaleX(0);
        itemView.setScaleY(0);
    }


    private void showTheItem(View itemView) {
        itemView.setVisibility(View.VISIBLE);
    }

    private void hideTheItem(View itemView) {
        itemView.setVisibility(View.GONE);
    }

    private boolean listEmpty() {
        return horizontalLinearLayout.getChildCount() == 0;
    }

    private final void makeItemVisible(final View itemView) {
        itemView.post(doMakeItemVisible(itemView));

    }

    private Runnable doMakeItemVisible(View itemView) {
        return new Runnable() {
            @Override
            public void run() {

                int finalWidth = itemView.getWidth();
                int finalHeight = itemView.getHeight();
                setWidthAndHeightToZero(itemView);
                animateWidthAndHeight(itemView, finalWidth, finalHeight, afterAnimateWidthAndHeight(itemView));
            }
        };
    }

    private Runnable afterAnimateWidthAndHeight(View itemView) {
        return new Runnable() {
            @Override
            public void run() {
                scaleToNormalSize(itemView);
            }
        };
    }

    private void scaleToNormalSize(View itemView) {
        itemView.animate().scaleX(1).scaleY(1).withEndAction(afterScaleToNormalSize(itemView));
    }

    private Runnable afterScaleToNormalSize(View itemView) {
        return new Runnable() {
            @Override
            public void run() {
                scrollItemIntoView(itemView);
            }
        };
    }

    private void setWidthAndHeightToZero(View itemView) {
        itemView.getLayoutParams().width = 0;
        itemView.getLayoutParams().height = 0;
        itemView.requestLayout();
    }

    private void scrollItemIntoView(View itemView) {
        scrollIntoView(itemView,scrollView,horizontalLinearLayout);
    }

    protected abstract void scrollIntoView(View itemView, HorizontalScrollView scrollView, ViewGroup itemList);


    //whenever the item is clicked, it is removed from the list and the new list is notified to the user
    private void handleItemClick(final DataType itemData, final View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemFromList(itemData, itemView);
            }
        });
    }

    private void removeItemFromList(DataType itemData, final View itemView) {
        listItems.remove(itemData);
        itemView.animate().scaleX(0).scaleY(0).withEndAction(new Runnable() {
            @Override
            public void run() {

                animateWidthAndHeight(itemView,0,0,new Runnable(){
                    @Override
                    public void run() {
                        horizontalLinearLayout.removeView(itemView);
                    }
                });

            }
        });
        onListChanged(listItems);
    }


    private void animateWidthAndHeight(final View targetView, int finalWidth, int finalHeight, final Runnable endAction) {

        ValueAnimator widthAnimator = ValueAnimator.ofInt(targetView.getWidth(),finalWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.getLayoutParams().width = (int)valueAnimator.getAnimatedValue();
                targetView.requestLayout();
            }
        });

        ValueAnimator heightAnimator = ValueAnimator.ofInt(targetView.getHeight(),finalHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.getLayoutParams().height = (int)valueAnimator.getAnimatedValue();
                targetView.requestLayout();
            }
        });


        widthAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(endAction != null){
                    endAction.run();
                }
            }
        });

        widthAnimator.start();
        heightAnimator.start();

    }


    protected abstract View createItemView(DataType itemData);
    protected abstract void onListChanged(List<DataType> listItems);

}
