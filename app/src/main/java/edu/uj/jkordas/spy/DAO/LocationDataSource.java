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
import edu.uj.jkordas.spy.POJO.Location;
import edu.uj.jkordas.spy.dbtables.CallTable;
import edu.uj.jkordas.spy.dbtables.LocationTable;
import edu.uj.jkordas.spy.developer.Logger;

public class LocationDataSource {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;
    private String[] allColumns = {LocationTable.COLUMN_ID, LocationTable.COLUMN_LONGITUDE,
            LocationTable.COLUMN_LATITUDE,
            LocationTable.COLUMN_TIMESTAMP};

    public LocationDataSource(Context context) {
        this.context = context;
        dbHelper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.execSQL("delete from " + LocationTable.TABLE_LOCATION);
//        Logger.log(this.context, "location table cleared");
    }

    public Location getLastLocation() {
        Cursor cursor = database.query(LocationTable.TABLE_LOCATION, allColumns,
                null, null, null, null, null);

        Location location = null;
        if (cursor.moveToLast()) {
            location = cursorToLocation(cursor);
        }

        cursor.close();
        return location;
    }

    public void addLocation(double longitude, double latitude) {
        Date date = new java.util.Date();
        long timestamp = date.getTime();

        ContentValues values = new ContentValues();
        values.put(LocationTable.COLUMN_LONGITUDE, String.valueOf(longitude));
        values.put(LocationTable.COLUMN_LATITUDE, String.valueOf(latitude));
        values.put(CallTable.COLUMN_TIMESTAMP, String.valueOf(timestamp));
        long insertId = database.insert(LocationTable.TABLE_LOCATION, null, values);
        Logger.log(this.context, "location saved to database");
    }

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<Location>();

        Cursor cursor = database.query(LocationTable.TABLE_LOCATION, allColumns, null, null, null,
                null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locationList.add(location);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return locationList;
    }

    private Location cursorToLocation(Cursor cursor) {
        String longitude = cursor.getString(1);
        String latitude = cursor.getString(2);
        int dayOfYear = cursor.getInt(3);
        Location currentLocation = new Location(Double.parseDouble(longitude),
                Double.parseDouble(latitude), dayOfYear);

        return currentLocation;
    }
}
