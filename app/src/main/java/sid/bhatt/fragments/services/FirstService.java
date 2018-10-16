package sid.bhatt.fragments.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import sid.bhatt.fragments.R;


public class FirstService extends Service {
    private String log = "permission";
    int i = 0;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(log, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(log, "onStartCommand" + Thread.currentThread().getId() + "" + Thread.currentThread().getId());

//        while (true) {
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.terachehra);
            mediaPlayer.start();
//            mediaPlayer.("/sdcard/Music/maine.mp3");//Write your location here
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();


            startForeground(1, notification);
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                          i++;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d(log, "running in service" +i);
//                }
//            }
//        }).start();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(log, "onStart");
        super.onStart(intent, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(log, "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(log, "onDestroy of service");
        mediaPlayer.stop();
        super.onDestroy();
    }
}
