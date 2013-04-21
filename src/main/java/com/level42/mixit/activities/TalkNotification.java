package com.level42.mixit.activities;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.level42.mixit.R;
import com.level42.mixit.models.Talk;

/**
 * Activité de l'alarme
 */
public class TalkNotification extends BroadcastReceiver {

    public static final String TALK_ID = "id";
    public static final String TALK_TITRE = "titre";
    public static final String TALK_SALLE = "salle";
    public static final String TALK_DATE = "date";

    /**
     * Ajoute une alarme pour notifier un talk
     * @param context
     * @param activity
     * @param talk
     * @param delay
     */
    public static void addTalkAlarmForNotification(Context context, Activity activity, Talk talk, Integer delay) {
        
        AlarmManager am = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TalkNotification.class);
        intent.putExtra(TalkNotification.TALK_ID, talk.getId().toString());
        intent.putExtra(TalkNotification.TALK_TITRE, talk.getTitle());
        intent.putExtra(TalkNotification.TALK_SALLE, talk.getSalleSession());
        intent.putExtra(TalkNotification.TALK_DATE, talk.getDateSession().getTime());
        
        long date = talk.getDateSession().getTime() - (delay.intValue() * 60 * 1000);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, talk.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {

        Long id = Long.valueOf(intent.getStringExtra(TalkNotification.TALK_ID));
        String titre = intent.getStringExtra(TalkNotification.TALK_TITRE);
        String salle = intent.getStringExtra(TalkNotification.TALK_SALLE);
        Long date = intent.getLongExtra(TalkNotification.TALK_DATE, 0);
        
        Intent showTalk = new Intent(context, TalkActivity.class);
        showTalk.putExtra(TalkActivity.TALK_ID, id);
        
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, showTalk, 0);
        
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        String dateNotification = String.format(context.getString(R.string.label_notification_date_talk), salle, format.format(new Date(date)));
        
        long[] patternVibrate = new long[] {0,500,110,500,110,450,110,200,110,170,40,450,110,200,110,170,40,500};
        
        NotificationCompat.Builder noti = new NotificationCompat.Builder(context)
                .setContentTitle(titre)
                .setContentText(dateNotification)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(date)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
                .setVibrate(patternVibrate);
        
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti.getNotification()); 
    }

}
