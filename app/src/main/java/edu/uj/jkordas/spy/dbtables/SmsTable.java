package edu.uj.jkordas.spy.dbtables;


public class SmsTable {

    public static final String TABLE_SMS = "sms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_ADDRESSEE = "addressee";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table " + TABLE_SMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SENDER + " text not null, "
            + COLUMN_ADDRESSEE + " text not null, " + COLUMN_CONTENT + " text not null, "
            + COLUMN_TIMESTAMP + " text not null" + ");";

}
