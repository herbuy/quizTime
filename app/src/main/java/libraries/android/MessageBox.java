package libraries.android;

import android.content.Context;
import android.widget.Toast;


public class MessageBox {
    public static void show(String message, Context context){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message, Context context){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
