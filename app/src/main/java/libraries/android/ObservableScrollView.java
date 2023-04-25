package libraries.android;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    public ObservableScrollView(Context context) {
        super(context);
    }

    private EventListener eventListener;

    public final void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        float percentScrollY = getPercentScrollY();
        if(y > oldY){
            invokeOnScrollDown(x, y, oldX, oldY, percentScrollY);
        }
        else if(y < oldY){
            invokeScrolledUp(x, y, oldX, oldY, percentScrollY);
        }

    }

    private void invokeScrolledUp(int x, int y, int oldX, int oldY, float percentScrollY) {
        if(eventListener != null){
            eventListener.onScrollUp(percentScrollY,x,y,oldX,oldY);
        }
    }

    private void invokeOnScrollDown(int x, int y, int oldX, int oldY, float percentScrollY) {
        if(eventListener != null){
            eventListener.onScrollDown(percentScrollY,x,y,oldX,oldY);
        }
    }

    public final float getPercentScrollY() {
        //lets check if it has a child
        if(getChildCount() < 1){
            return 0;
        }


        //then compute the height of the child. To test, we can pass in a random child
        View child = getChildAt(getChildCount() - 1);
        int contentHeight = child.getHeight();

        //then compute the height of the scroll view
        int containerHeight = getHeight();

        //compute max scroll as difference between height of child and height of scroll view
        int maxScrollY = Math.max(0,contentHeight - containerHeight);

        //now check the current scroll, which is scrolly y
        int currentScrollY = getScrollY();

        //compute what percentange of the overall it is
        return  100f * currentScrollY / maxScrollY;
    }

    public interface EventListener{

        void onScrollDown(float percentScrollY, int x, int y, int oldX, int oldY);
        void onScrollUp(float percentScrollY, int x, int y, int oldX, int oldY);
    }
}
