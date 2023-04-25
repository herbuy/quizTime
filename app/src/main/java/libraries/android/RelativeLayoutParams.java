package libraries.android;


import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Random;

public class RelativeLayoutParams {

    private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public static RelativeLayoutParams create(){
        return new RelativeLayoutParams();
    }

    public RelativeLayoutParams matchWidthToParent(){
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        return this;
    }
    public RelativeLayoutParams matchHeightToParent(){
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        return this;
    }

    public RelativeLayoutParams wrapWidthToContent(){
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        return this;
    }
    public RelativeLayoutParams wrapHeightToContent(){
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        return this;
    }

    public RelativeLayoutParams alignParentBottom(){
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return this;
    }
    public RelativeLayoutParams alignParentTop(){
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        return this;
    }
    public RelativeLayoutParams alignParentLeft(){
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        return this;
    }
    public RelativeLayoutParams alignParentRight(){
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        return this;
    }
    public RelativeLayoutParams alignAbove(View view){
        assignIdGivenViewHasNoId(view);
        params.addRule(RelativeLayout.ABOVE,view.getId());
        return this;
    }
    public RelativeLayoutParams alignRightOf(View view){
        assignIdGivenViewHasNoId(view);
        params.addRule(RelativeLayout.RIGHT_OF,view.getId());
        return this;
    }
    public RelativeLayoutParams alignLeftOf(View view){
        assignIdGivenViewHasNoId(view);
        params.addRule(RelativeLayout.LEFT_OF,view.getId());
        return this;
    }
    public RelativeLayoutParams alignBelow(View view){
        assignIdGivenViewHasNoId(view);
        params.addRule(RelativeLayout.BELOW,view.getId());
        return this;
    }

    private void assignIdGivenViewHasNoId(View view) {
        if(view.getId() == 0 || view.getId() == -1){
            view.setId(randomId());
        }
    }

    private int randomId() {
        int val = Math.abs(new Random().nextInt());
        return val != 0 ? val : randomId();
    }

    public RelativeLayout.LayoutParams build(){
        return params;
    }

    public RelativeLayoutParams centerVertical() {
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        return this;
    }
    public RelativeLayoutParams centerHorizontal() {
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        return this;
    }

    public RelativeLayoutParams setWidth(int width) {
        params.width = width;
        return this;
    }
    public RelativeLayoutParams setHeight(int height) {
        params.height = height;
        return this;
    }

    public RelativeLayoutParams setMargins(int left, int top, int right, int bottom) {
        params.setMargins(left,top,right,bottom);
        return this;
    }
}


