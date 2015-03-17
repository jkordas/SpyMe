package edu.uj.jkordas.spy.developer;

import android.content.Context;
import android.widget.Toast;

public class Logger {

    public static void log(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
