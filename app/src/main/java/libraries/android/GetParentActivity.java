package libraries.android;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

public class GetParentActivity {
    public static Activity fromView(View view){
        return fromContext(view.getContext());
    }

    public static Activity fromContext(Context context) {
        return fromContext(context, Activity.class);
    }

    //we might want a particular subclass of the activity class
    public static <T extends Activity> T fromContext(Context context, Class<T> toClass) {

        while (context instanceof ContextWrapper) {
            try{
                return (T)context;
            }
            catch (Throwable t){

            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
