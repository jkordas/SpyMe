package edu.uj.jkordas.spy.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import edu.uj.jkordas.spy.R;

public class PrefActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
