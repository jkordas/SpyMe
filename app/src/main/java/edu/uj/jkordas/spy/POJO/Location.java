package edu.uj.jkordas.spy.POJO;

public class Location {
    private long id;
    private double longitude;
    private double latitude;
    private int dayOfYear;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public Location(double _longitude, double _latitude, int _dayOfYear) {
        this.longitude = _longitude;
        this.latitude = _latitude;
        this.dayOfYear = _dayOfYear;
    }

    @Override
    public String toString() {
        return "[" + this.longitude + ", " + this.latitude + "]";
    }
}
