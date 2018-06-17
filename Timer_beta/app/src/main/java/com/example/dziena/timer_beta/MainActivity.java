package com.example.dziena.timer_beta;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private IntentFilter filter =
            new IntentFilter("com.example.dziena.timer_beta.MainActivity");

    private BroadcastReceiver broadcast = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Alarm aktywowany")
                    .setContentText("Wystaw śmieci")
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setContentInfo("Info");

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1,builder.build());
        }
    };
    private Button mAlarm;

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcast, filter);
        //nie interesuje nas tutaj czym jest filter, widzimy jednak że rejestrujemy nasz receiver w kodzie.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlarm = (Button)findViewById(R.id.uruchom_alarm);
        View.OnClickListener mAkcjaPrzycisku = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "Alarm włączony",
                        Toast.LENGTH_SHORT).show();
                startAlarm();
                mAlarm.setText("Alarm uruchomiony");
            }
        };
        mAlarm.setOnClickListener(mAkcjaPrzycisku);
    }

//    @Override
//    public void onPause() {
//        unregisterReceiver(broadcast);
//        // trzeba zawsze po sobie posprzątać w tym przypadku wyrejestrować receiver.
//        super.onPause();
//    }
    private void startAlarm(){

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(MainActivity.this,MainActivity.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0, i, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime()+100,100,pi);

    }
}
