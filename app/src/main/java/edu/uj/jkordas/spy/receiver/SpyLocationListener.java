package edu.uj.jkordas.spy.receiver;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import edu.uj.jkordas.spy.DAO.LocationDataSource;
import edu.uj.jkordas.spy.developer.Logger;

public class SpyLocationListener implements LocationListener {
    private Context context;

    public SpyLocationListener(Context _context) {
        context = _context;
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Logger.log(context, lat + ", " + lng);
        // save data to db
        LocationDataSource dataSource = new LocationDataSource(context);
        dataSource.open();
        dataSource.addLocation(lng, lat);
        dataSource.close();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Logger.log(context, provider + "disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Logger.log(context, provider + "enabled");
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

}
