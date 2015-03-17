package edu.uj.jkordas.spy.dbtables;

public class LocationTable {

    public static final String TABLE_LOCATION = "location";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table " + TABLE_LOCATION + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LONGITUDE + " text not null, "
            + COLUMN_LATITUDE + " text, " + COLUMN_TIMESTAMP + " text not null" + ");";

}
