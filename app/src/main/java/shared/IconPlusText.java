package shared;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import libraries.android.MakeDummy;
import libraries.android.RelativeLayoutParams;

/**
 * This class is very powwerful for creating layouts for items and items in list
 * You can alter and customize a number of things i.e
 * -Orientation [customize to one of 4] -- determine whether the icon is on left of text, on right of text, above the text, or below the text
 * -what icon to use
 * -icon size [width and height]
 * -perform any other modifications you want on the icon, title, subtitle, or entire widget as a whole
 * <p>
 * In has been used to create headers for pages, action bars, items in a list, profile of an item, etc
 * examples:
 * -------------
 * Orientation.VERTICAL_STARTING_WITH_TEXT has been used to present profile info about an item
 * Orientation.VERTICAL_STARTING_WITH_ICON has been used to create custom tabs
 * Orientation.HORIZONTAL_STARTING_WITH_TEXT has been used to show screen header with humberger menu as icon on right
 * Orientation.HORIZONTAL_STARTING_WITH_ICON has been used to create list itmes and menu options
 * <p>
 * We have generally used ICON + TEXT to:
 * -set image to a tick when the item is selected by clicking it or otherwise remove the check mark to indicate unselected
 * <p>
 * - we use icon plus text to show error messages
 */

public abstract class IconPlusText {
    protected final Context context;
    RelativeLayout layout;

    public IconPlusText(Context context) {
        this.context = context;
        createView();

    }

    public IconPlusText(Context context, boolean autoInit) {
        this.context = context;
        if (autoInit) {
            createView();
        }
    }


    public void createView() {
        layout = new RelativeLayout(context);
        layout.addView(imageArea());
        layout.addView(textArea());
        layout.setPadding(paddingLeft(), paddingTop(), paddingRight(), paddingBottom());

        doLayout();
    }



    protected int paddingLeft() {
        return Dp.two_em();
    }

    protected int paddingTop() {
        return Dp.scaleBy(1.5f);
    }

    protected int paddingRight() {
        return Dp.two_em();
    }

    protected int paddingBottom() {
        return Dp.scaleBy(1.5f);
    }

    public enum Orientation {
        HORIZONTAL_STARTING_WITH_ICON,
        HORIZONTAL_STARTING_WITH_TEXT,
        VERTICAL_STARTING_WITH_ICON,
        VERTICAL_STARTING_WITH_TEXT,
    }

    protected Orientation getOrientation() {

        return Orientation.HORIZONTAL_STARTING_WITH_ICON;
    }

    private void doLayout() {
        if (getOrientation() == Orientation.HORIZONTAL_STARTING_WITH_TEXT) {
            textArea.setLayoutParams(new RelativeLayoutParams()
                    .alignLeftOf(imageArea)
                    .alignParentLeft()
                    .build()
            );
            textArea.setPadding(0, 0, spaceBetweenIconAndText(), 0);
            imageArea.setLayoutParams(new RelativeLayoutParams()
                    .alignParentRight()
                    //.setWidth(defaultIconWidth())
                    //.setHeight(defaultIconHeight())
                    .build()
            );
        } else if (getOrientation() == Orientation.VERTICAL_STARTING_WITH_ICON) {
            textArea.setLayoutParams(
                    centerHorizontalIfVertical() ?
                            new RelativeLayoutParams().alignBelow(imageArea).centerHorizontal().build()
                            :
                            new RelativeLayoutParams().alignBelow(imageArea).build()
            );
            textArea.setPadding(0, 0, 0, 0);
            imageArea.setLayoutParams(
                    centerHorizontalIfVertical() ?
                            imageParamsIfVerticalStartingWithIcon().centerHorizontal().build()
                            :
                            imageParamsIfVerticalStartingWithIcon().build()
            );

        } else if (getOrientation() == Orientation.VERTICAL_STARTING_WITH_TEXT) {

            textArea.setLayoutParams(
                    centerHorizontalIfVertical() ?
                            new RelativeLayoutParams().alignParentTop().centerHorizontal().build()
                            :
                            new RelativeLayoutParams().alignParentTop()
                                    .build()

            );
            textArea.setPadding(0, 0, 0, 0);
            imageArea.setLayoutParams(
                    centerHorizontalIfVertical() ?
                            imageParamsIfVerticalStartingWithText().centerHorizontal().build() :
                            imageParamsIfVerticalStartingWithText().build()
            );
        } else {
            textArea.setLayoutParams(new RelativeLayoutParams()
                    .alignRightOf(imageArea)
                    .build()
            );
            textArea.setPadding(spaceBetweenIconAndText(), 0, 0, 0);
            imageArea.setLayoutParams(new RelativeLayoutParams()
                    .alignParentLeft()
                    //.setWidth(defaultIconWidth())
                    //.setHeight(defaultIconHeight())
                    .build()
            );
        }

    }

    protected int spaceBetweenIconAndText() {
        return Dp.two_em();
    }

    private RelativeLayoutParams imageParamsIfVerticalStartingWithIcon() {
        return new RelativeLayoutParams()
                .alignParentTop()
                //.setWidth(defaultIconWidth())
                //.setHeight(defaultIconHeight())
                ;
    }

    private RelativeLayoutParams imageParamsIfVerticalStartingWithText() {
        return new RelativeLayoutParams()
                .alignBelow(textArea)
                //.setWidth(defaultIconWidth())
                //.setHeight(defaultIconHeight())
                ;
    }

    protected boolean centerHorizontalIfVertical() {
        return true;
    }

    LinearLayout textArea;

    private View textArea() {
        textArea = MakeDummy.linearLayoutVertical(
                context,
                title(),
                subTitle()
        );
        modifyRightarea(textArea);
        return textArea;
    }

    protected void modifyRightarea(ViewGroup rightArea) {

    }

    TextView subtitle;

    private View subTitle() {
        subtitle = Atom.textView(context, "Sub title goes here",0.8f);
        if (isVerticalOrientation() && centerHorizontalIfVertical()) {
            subtitle.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        modifySubtitle(subtitle);
        return subtitle;
    }

    public final TextView getSubtitle() {
        return subtitle;
    }

    /**
     * used to set default gravity to center horizontal for title and subtitle
     */
    private boolean isVerticalOrientation() {
        return getOrientation() == Orientation.VERTICAL_STARTING_WITH_ICON || getOrientation() == Orientation.VERTICAL_STARTING_WITH_TEXT;
    }


    private TextView title;

    private View title() {
        title = Atom.textViewPrimaryBold(context, "Title goes here");
        Dp.setTextSize(title,0.9f);
        if (isVerticalOrientation() && centerHorizontalIfVertical()) {
            title.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        modifyTheTitle(title);
        return title;
    }

    public final TextView getTitle() {
        return title;
    }

    ImageView imageView;
    ViewGroup imageArea;

    private View imageArea() {
        imageArea = new LinearLayout(context);
        ((LinearLayout)imageArea).setOrientation(LinearLayout.VERTICAL);
        imageArea.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageArea.addView(doImageView());
        modifyImageArea(imageArea);
        return imageArea;
    }

    protected void modifyImageArea(ViewGroup imageArea) {

    }

    private View doImageView() {
        imageView = createImageView();
        imageView.setImageResource(getImageResource());
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(defaultIconWidth(),defaultIconHeight()));
        modifyImageView(imageView);
        return getModifiedImageArea(imageView);
    }

    protected View getModifiedImageArea(ImageView imageView) {
        return imageView;
    }

    protected ImageView createImageView() {
        return new ImageView(context);
    }

    public final ImageView getImageView() {
        return imageView;
    }

    protected int defaultIconWidth() {

        return Dp.scaleBy(6);
    }

    protected int defaultIconHeight() {
        return defaultIconWidth();
    }

    public View getView() {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.addView(layout);
        modifyContainer(container);
        return container;
    }

    protected abstract int getImageResource();

    protected abstract void modifyTheTitle(TextView title);

    protected abstract void modifySubtitle(TextView subtitle);

    protected abstract void modifyImageView(ImageView imageView);

    protected abstract void modifyContainer(View container);


}

