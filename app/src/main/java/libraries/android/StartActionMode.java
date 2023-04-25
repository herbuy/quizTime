package libraries.android;


import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.View;

public class StartActionMode {
    public static ActionMode run(View sender, Callback callback){
        return sender.startActionMode(callback);
    }
}
