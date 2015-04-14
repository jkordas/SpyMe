package edu.uj.jkordas.spy.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    private static final String EMPTY_STRING = "_empty_string_";
    private static final String defaultSupervisorNumber = "+48500582367";
    private static final String defaultSupervisorPrefix = "_";
    private static final String defaultSecretCode = "1010";

    public static final String phoneNumber = "000000000";

    public static String getSupervisorNumber(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String supervisorNumber = prefs.getString("supervisor_number", EMPTY_STRING);
        if (supervisorNumber.equals(EMPTY_STRING)) {
            return defaultSupervisorNumber;
        }

        return supervisorNumber;
    }

    public static String getSupervisorPrefix(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String supervisorPrefix = prefs.getString("supervisor_prefix", EMPTY_STRING);
        if (supervisorPrefix.equals(EMPTY_STRING)) {
            return defaultSupervisorPrefix;
        }

        return supervisorPrefix;
    }

    public static String getSecretCode(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String secretCode = prefs.getString("secret_code", EMPTY_STRING);
        if (secretCode.equals(EMPTY_STRING)) {
            return defaultSecretCode;
        }

        return secretCode;
    }
}
