package edu.umd.mguenzel.gymqueue;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Alan Zhang on 5/9/2016.
 */
public class Notification extends Service {

    private int Notif_ID = 999;
    public String Tag = "Reservation";
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getBooleanExtra(Tag, false)) {
            showNotification();
        }

        return 0;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        CharSequence title = "Reservation Approaching";
        int icon = R.drawable.gym_64;
        CharSequence text = "5 Minutes until Reservation";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(icon);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);

        mNotificationManager.notify(Notif_ID, mBuilder.build());
    }
}
