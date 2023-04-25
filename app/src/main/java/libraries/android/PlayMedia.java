package libraries.android;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;

public class PlayMedia {

    public static void defaultNotification(Context context){
        try{
            MediaPlayer player = MediaPlayer.create(
                    context,
                    Settings.System.DEFAULT_NOTIFICATION_URI
            );
            player.start();
        }
        catch(Throwable t){
            return;
        }
    }
}
