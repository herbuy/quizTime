package libraries.android;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Locale;

public class PlayRingtone {
    public static void fromResourceId(Context context, int resourceId) {
        Ringtone ringtone = RingtoneManager.getRingtone(context, Uri.parse(
                String.format(
                        Locale.ENGLISH,
                        "android.resource://%s/%d",
                        context.getPackageName(),
                        resourceId
                )
        ));

        ringtone.play();
    }
}
