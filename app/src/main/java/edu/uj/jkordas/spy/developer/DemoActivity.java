package edu.uj.jkordas.spy.developer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import edu.uj.jkordas.spy.AsyncSendMail;
import edu.uj.jkordas.spy.R;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        return true;
    }

    public void sendReport(View view) {
        new AsyncSendMail(getApplicationContext()).execute();
        Logger.log(this.getApplicationContext(), "report has been send");
    }

    public void showMessages(View view) {
        Intent intent = new Intent(this, LogsActivity.class);
        intent.putExtra(LogsActivity.TYPE, LogsActivity.MESSAGES_TYPE);

        startActivity(intent);
    }

    public void showCalls(View view) {
        Intent intent = new Intent(this, LogsActivity.class);
        intent.putExtra(LogsActivity.TYPE, LogsActivity.CALLS_TYPE);

        startActivity(intent);
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, LogsActivity.class);
        intent.putExtra(LogsActivity.TYPE, LogsActivity.HISTORY_TYPE);

        startActivity(intent);
    }

    public void showLocations(View view) {
        Intent intent = new Intent(this, LogsActivity.class);
        intent.putExtra(LogsActivity.TYPE, LogsActivity.LOCATIONS_TYPE);

        startActivity(intent);
    }

}
