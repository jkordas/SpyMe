package edu.uj.jkordas.spy.dbtables;

public class BrowserHistoryTable {

	public static final String TABLE_BROWSER_HISTORY = "browser_history";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_TIMESTAMP = "timestamp";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_BROWSER_HISTORY + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TITLE + " text, "
			+ COLUMN_URL + " text not null, " + COLUMN_TIMESTAMP
			+ " text not null" + ");";

}
