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
import edu.uj.jkordas.spy.POJO.Sms;
import edu.uj.jkordas.spy.dbtables.SmsTable;
import edu.uj.jkordas.spy.developer.Logger;


public class SmsDataSource {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;
    private String[] allColumns = {SmsTable.COLUMN_ID, SmsTable.COLUMN_SENDER,
            SmsTable.COLUMN_ADDRESSEE, SmsTable.COLUMN_CONTENT, SmsTable.COLUMN_TIMESTAMP};

    public SmsDataSource(Context context) {
        this.context = context;
        dbHelper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addSMS(String sender, String addressee, String content) {
        Date date = new java.util.Date();
        long timestamp = date.getTime();

        ContentValues values = new ContentValues();
        values.put(SmsTable.COLUMN_SENDER, sender);
        values.put(SmsTable.COLUMN_ADDRESSEE, addressee);
        values.put(SmsTable.COLUMN_CONTENT, content);
        values.put(SmsTable.COLUMN_TIMESTAMP, String.valueOf(timestamp));
        long insertId = database.insert(SmsTable.TABLE_SMS, null, values);
        Logger.log(this.context, "sms saved to database");
        /*
		 * Cursor cursor = database.query(SMSDatabaseOpenHelper.TABLE_SMS,
		 * allColumns, SMSDatabaseOpenHelper.COLUMN_ID + " = " + insertId, null,
		 * null, null, null); cursor.moveToFirst(); Comment newComment =
		 * cursorToComment(cursor); cursor.close(); return newComment;
		 */
    }

    public void clearTable() {
        database.execSQL("delete from " + SmsTable.TABLE_SMS);
        Logger.log(this.context, "sms table cleared");
    }

	/*
	 * public void deleteComment(Comment comment) { long id = comment.getId();
	 * System.out.println("Comment deleted with id: " + id);
	 * database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID +
	 * " = " + id, null); }
	 */

    public List<Sms> getAllSMS() {
        List<Sms> smsList = new ArrayList<Sms>();

        Cursor cursor = database
                .query(SmsTable.TABLE_SMS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sms sms = cursorToSMS(cursor);
            smsList.add(sms);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return smsList;
    }

    private Sms cursorToSMS(Cursor cursor) {
        Sms currentSMS = new Sms();
        currentSMS.setId(cursor.getLong(0));
        currentSMS.setSender(cursor.getString(1));
        currentSMS.setAddressee(cursor.getString(2));
        currentSMS.setContent(cursor.getString(3));
        currentSMS.setTimestamp(cursor.getString(4));
        return currentSMS;
    }
}
