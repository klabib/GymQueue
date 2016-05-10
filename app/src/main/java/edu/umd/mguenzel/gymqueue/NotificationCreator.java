package edu.umd.mguenzel.gymqueue;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Alan Zhang on 5/10/2016.
 */
public class NotificationCreator extends IntentService {
    private static final int NOTIF_ID = 999;

    public NotificationCreator(){
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        long time = System.currentTimeMillis();
        int icon = R.drawable.gym_64;

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("GymQueue Reminder")
                .setContentText("It is almost time for your reservation")
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        nm.notify(NOTIF_ID, notification);
    }

}