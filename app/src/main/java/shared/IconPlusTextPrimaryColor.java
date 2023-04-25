package shared;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public abstract class IconPlusTextPrimaryColor extends IconPlusText {
    public IconPlusTextPrimaryColor(Context context) {
        super(context);
        getTitle().setTextColor(Color.WHITE);
        getSubtitle().setTextColor(Color.WHITE);

    }

    @Override
    protected final void modifyContainer(View container) {
        container.setBackgroundColor(ItemColor.primary());
    }
}
