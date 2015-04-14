package edu.uj.jkordas.spy.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uj.jkordas.spy.DatabaseOpenHelper;
import edu.uj.jkordas.spy.POJO.Call;
import edu.uj.jkordas.spy.dbtables.CallTable;
import edu.uj.jkordas.spy.developer.Logger;

public class CallDataSource {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;
    private String[] allColumns = {CallTable.COLUMN_ID, CallTable.COLUMN_NUMBER,
            CallTable.COLUMN_CONTACT_NAME, CallTable.COLUMN_TYPE, CallTable.COLUMN_DURATION,
            CallTable.COLUMN_TIMESTAMP};

    public CallDataSource(Context context) {
        this.context = context;
        dbHelper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addCall(String number, String contactName, int type, int duration) {
        Date date = new java.util.Date();
        long timestamp = date.getTime();

        ContentValues values = new ContentValues();
        values.put(CallTable.COLUMN_NUMBER, number);
        values.put(CallTable.COLUMN_CONTACT_NAME, contactName);
        values.put(CallTable.COLUMN_TYPE, type);
        values.put(CallTable.COLUMN_DURATION, duration);
        values.put(CallTable.COLUMN_TIMESTAMP, String.valueOf(timestamp));
        long insertId = database.insert(CallTable.TABLE_CALL, null, values);
        Logger.log(this.context, "call saved to database");
    }

    public void clearTable() {
        database.execSQL("delete from " + CallTable.TABLE_CALL);
//        Logger.log(this.context, "call table cleared");
    }

    public List<Call> getAllCalls() {
        List<Call> callList = new ArrayList<Call>();

        Cursor cursor = database.query(CallTable.TABLE_CALL, allColumns, null, null, null, null,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Call call = cursorToCall(cursor);
            callList.add(call);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return callList;
    }

    private Call cursorToCall(Cursor cursor) {
        String number = cursor.getString(1);
        int type = cursor.getInt(3);
        Call currentCall = new Call(number, type);

        currentCall.setId(cursor.getLong(0));
        currentCall.setContactName(cursor.getString(2));
        currentCall.setDuration(cursor.getInt(4));
        return currentCall;
    }

    public Call getLastCall() {

        Cursor cursor = database.query(CallTable.TABLE_CALL, allColumns, null,
                null, null, null, null);

        Call call = null;
        if (cursor.moveToLast()) {
            call = cursorToCall(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        return call;
    }
}
