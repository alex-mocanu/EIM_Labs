package ro.pub.cs.systems.eim.lab10.googlemapplaces.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Place {

    private double latitude;
    private double longitude;
    private String name;
    private float markerType;

    public Place() {
        latitude = 0.0;
        longitude = 0.0;
        name = new String();
        markerType = BitmapDescriptorFactory.HUE_RED;
    }

    public Place(double latitude, double longitude, String name, float markerType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.markerType = markerType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMarkerType() {
        return markerType;
    }

    public void setMarkerType(float markerType) {
        this.markerType = markerType;
    }

    public String toString() {
        return name + "(" + latitude + ", " + longitude + ")";
    }
}
