
package libraries.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class HorizontalList2<DataType> {

    private final Context context;
    private HorizontalScrollView scrollView;

    public HorizontalList2(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        scrollView = createScrollView();
        scrollView.addView(tableLayout());
    }

    private HorizontalScrollView createScrollView() {
        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollView.setHorizontalScrollBarEnabled(false);
        return scrollView;

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

    private List<DataType> listItems = new ArrayList<>();


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public synchronized boolean contains(DataType selectionInfo) {
        return listItems.contains(selectionInfo);
    }

    public synchronized DataType findFirst(DataType item2) {
        for(DataType item : listItems){
            if(item.equals(item2)){
                return item;
            }
        }
        return null;
    }

    public synchronized List<DataType> findAll(DataType item2) {
        List<DataType> results = new ArrayList<>();
        for(DataType item : listItems){
            if(item.equals(item2)){
                results.add(item);
            }
        }
        return results;
    }


    public synchronized final void add(final DataType itemData){
        //add the item to the list
        //then redraw the visible list
        //then scroll to the last item in the list

        listItems.add(itemData);
        updateView();
        makeItemVisible(lastChild());
        onItemAdded(itemData,listItems);


        /*final View itemView = createItemView(itemData);
        if(horizontalLinearLayout.getChildCount() == 0){
            itemView.setVisibility(View.GONE);
            Sdk.beginDelayedTransition(horizontalLinearLayout);
            horizontalLinearLayout.addView(itemView);
            itemView.setVisibility(View.VISIBLE);

        }
        else{
            horizontalLinearLayout.addView(itemView);
            scrollItemIntoView(itemView);
        }
        handleItemClick(itemData, itemView);

        makeItemVisible(itemView);
        onItemAdded(itemData, listItems);*/
    }

    private View lastChild() {
        return horizontalLinearLayout.getChildAt(horizontalLinearLayout.getChildCount() -1);
    }


    private void updateView() {
        horizontalLinearLayout.removeAllViews();
        for(DataType itemData : listItems){
            horizontalLinearLayout.addView(createItemView(itemData));
        }
    }

    public void remove(DataType itemData) {
        removeItems(itemData);
    }

    private synchronized void removeItems(DataType itemData) {
        List<DataType> matchingItems = findAll(itemData);
        for(final DataType item : matchingItems){
            //gradually hide the matching view, then remove the item from the list, then fire item removed
            final View childView = childViews.get(item);
            if(childView != null){
                childView.animate().scaleX(0).scaleY(0).withEndAction(onAnimateWidthHeightToZero(item, childView));
            }

        }
    }

    private Runnable onAnimateWidthHeightToZero(final DataType item, final View childView) {
        return new Runnable() {
            @Override
            public void run() {
                animateWidthAndHeight(childView,0,0, onRemoveItemFromList(item));
            }
        };
    }

    private Runnable onRemoveItemFromList(final DataType item) {
        return new Runnable() {
            @Override
            public void run() {
                doRemoveItem(item);
            }
        };
    }

    private void doRemoveItem(DataType item) {
        listItems.remove(item);
        updateView();
        onItemRemoved(item,listItems);
    }


    private Map<DataType, View> childViews = new LinkedHashMap<>();

    private View createItemView(final DataType itemData) {
        final View itemView = viewFromItem(itemData);
        childViews.put(itemData,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(itemData);
            }
        });
        return itemView;
    }

    private final void makeItemVisible(final View itemView) {

        itemView.post(new Runnable() {
            @Override
            public void run() {

                int finalWidth = itemView.getWidth();
                int finalHeight = itemView.getHeight();

                animateWidthAndHeight(itemView,0,0, finalWidth, finalHeight, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }

    private final void makeItemVisible0(final View itemView) {
        itemView.post(new Runnable() {
            @Override
            public void run() {
                //create space for the item using animation


                int finalWidth = itemView.getWidth();
                int finalHeight = itemView.getHeight();
                itemView.getLayoutParams().width = 0;
                itemView.getLayoutParams().height = 0;
                itemView.requestLayout();


                animateWidthAndHeight(itemView, finalWidth, finalHeight, new Runnable() {
                    @Override
                    public void run() {
                        //fill the space for the item using animation

                        itemView.animate().scaleX(1).scaleY(1).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                scrollItemIntoView(itemView);
                            }
                        });
                    }
                });
            }
        });

    }

    private void scrollItemIntoView(View itemView) {
        scrollIntoView(itemView,scrollView);
    }




    //whenever the item is clicked, it is removed from the list and the new list is notified to the user

    private void animateWidthAndHeight(final View targetView, int fromWidth, int fromHeight, int finalWidth, int finalHeight, final Runnable endAction) {

        ValueAnimator widthAnimator = ValueAnimator.ofInt(fromWidth,finalWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.getLayoutParams().width = (int)valueAnimator.getAnimatedValue();
                targetView.requestLayout();
            }
        });

        ValueAnimator heightAnimator = ValueAnimator.ofInt(fromHeight,finalHeight);
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

    private void animateWidthAndHeight(final View targetView, int finalWidth, int finalHeight, final Runnable endAction) {
       animateWidthAndHeight(targetView,targetView.getWidth(),targetView.getHeight(),finalWidth,finalHeight,endAction);
    }

    protected abstract View viewFromItem(DataType itemData);
    protected abstract void onItemAdded(DataType itemData, List<DataType> listItems);
    protected abstract void onItemRemoved(DataType itemData, List<DataType> listItems);
    protected abstract void scrollIntoView(View itemView, HorizontalScrollView scrollView);

}
