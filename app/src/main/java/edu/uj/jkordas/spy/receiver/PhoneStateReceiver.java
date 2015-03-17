package edu.uj.jkordas.spy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import edu.uj.jkordas.spy.developer.Logger;

public class PhoneStateReceiver extends BroadcastReceiver {
    private int lastPhoneState = -1;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);

            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private Context context;

        public MyPhoneStateListener(Context context) {
            super();
            this.context = context;
        }

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);
            Log.d("MyPhoneListener", "last phone state: " + lastPhoneState);

            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Logger.log(context, "ringing");
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Logger.log(context, "offhook");
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                Logger.log(context, "idle");
            }

            lastPhoneState = state;
        }
    }

}
