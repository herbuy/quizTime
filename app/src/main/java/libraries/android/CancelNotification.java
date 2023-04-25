package libraries.android;

import android.app.NotificationManager;
import android.content.Context;

public class CancelNotification {
    public static void where(Context context, String notificationId) {
        where(context, Integer.valueOf(notificationId));
    }

    public static void where(Context context, int notificationId){
        NotificationManager notifMgr = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notifMgr.cancel(notificationId);
    }

    public static void all(Context context){
        NotificationManager notifMgr = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notifMgr.cancelAll();
    }
}
