package edu.uj.jkordas.spy.dbtables;


public class CallTable {

    public static final String TABLE_CALL = "call";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_CONTACT_NAME = "contact_name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table " + TABLE_CALL + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NUMBER + " text not null, "
            + COLUMN_CONTACT_NAME + " text, " + COLUMN_TYPE + " int not null, " + COLUMN_DURATION
            + " int, " + COLUMN_TIMESTAMP + " text not null" + ");";

}
