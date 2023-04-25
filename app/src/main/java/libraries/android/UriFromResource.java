package libraries.android;

import android.content.Context;
import android.net.Uri;

import java.util.Locale;

public class UriFromResource {

    public static Uri get(Context context, int resourceId) {

        return Uri.parse(
                String.format(
                        Locale.ENGLISH,
                        "android.resource://%s/%d",
                        context.getPackageName(),
                        resourceId
                )
        );
    }
}
