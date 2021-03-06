package com.example.cs4084;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class TrackJourneyService extends Service {
    private static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Sound notification alert
        MediaPlayer mp = MediaPlayer.create(this,R.raw.notification_alert);
        mp.start();

        // Create notification to appear when timer elapses
        Intent popupIntent = new Intent(this.getApplicationContext(), ConfirmArrivalActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, popupIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification_foreground);
        builder.setColor(14029665);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setContentText(getString(R.string.notification_text));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setOngoing(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0 , builder.build());
        return START_NOT_STICKY;
    }
}