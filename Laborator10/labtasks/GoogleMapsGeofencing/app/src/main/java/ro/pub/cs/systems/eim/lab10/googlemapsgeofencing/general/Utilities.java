package ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Random;

import ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.view.GoogleMapsActivity;

public class Utilities {

    public static String generateGeofenceIdentifier(int length) {
        StringBuffer result = new StringBuffer("");

        Random random = new Random();
        for (int index = 0; index < length; index++) {
            result.append((char) (Constants.FIRST_LETTER + random.nextInt(Constants.ALPHABET_LENGTH)));
        }
        return result.toString();
    }

    public static int getApplicationVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(Constants.TAG, "An exception has occurred: " + nameNotFoundException.getMessage());
            if (Constants.DEBUG) {
                nameNotFoundException.printStackTrace();
            }
        }
        return -1;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                GoogleMapsActivity.class.getSimpleName(),
                Context.MODE_PRIVATE
        );
    }

    public static Location getLastLocationFromSharedPreferences(Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        String lastLocation = sharedPreferences.getString(Constants.LAST_LOCATION, "");
        if (lastLocation.isEmpty()) {
            Log.i(Constants.TAG, Constants.LAST_LOCATION_ERROR_MESSAGE);
            return null;
        }
        int registeredApplicationVersion = sharedPreferences.getInt(Constants.APPLICATION_VERSION_PROPERTY, Integer.MIN_VALUE);
        int currentVersion = getApplicationVersion(context);
        if (registeredApplicationVersion != currentVersion) {
            Log.i(Constants.TAG, Constants.APPLICATION_VERSION_ERROR_MESSAGE);
            return null;
        }
        Gson gson = new Gson();
        Location location = gson.fromJson(lastLocation, Location.class);
        return location;
    }

    public static boolean getGeofenceStatusFromSharedPreferences(Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean geofenceStatus = sharedPreferences.getBoolean(Constants.GEOFENCE_STATUS, false);
        int registeredApplicationVersion = sharedPreferences.getInt(Constants.APPLICATION_VERSION_PROPERTY, Integer.MIN_VALUE);
        int currentVersion = getApplicationVersion(context);
        if (registeredApplicationVersion != currentVersion) {
            Log.i(Constants.TAG, Constants.APPLICATION_VERSION_ERROR_MESSAGE);
            return false;
        }
        return geofenceStatus;
    }

    public static String getGeofenceLatitudeFromSharedPreferences(Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        String geofenceLatitude = sharedPreferences.getString(Constants.GEOFENCE_LATITUDE, "");
        if (geofenceLatitude.isEmpty()) {
            Log.i(Constants.TAG, Constants.GEOFENCE_LATITUDE_ERROR_MESSAGE);
            return null;
        }
        int registeredApplicationVersion = sharedPreferences.getInt(Constants.APPLICATION_VERSION_PROPERTY, Integer.MIN_VALUE);
        int currentVersion = getApplicationVersion(context);
        if (registeredApplicationVersion != currentVersion) {
            Log.i(Constants.TAG, Constants.APPLICATION_VERSION_ERROR_MESSAGE);
            return null;
        }
        return geofenceLatitude;
    }

    public static String getGeofenceLongitudeFromSharedPreferences(Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        String geofenceLongitude = sharedPreferences.getString(Constants.GEOFENCE_LONGITUDE, "");
        if (geofenceLongitude.isEmpty()) {
            Log.i(Constants.TAG, Constants.GEOFENCE_LONGITUDE_ERROR_MESSAGE);
            return null;
        }
        int registeredApplicationVersion = sharedPreferences.getInt(Constants.APPLICATION_VERSION_PROPERTY, Integer.MIN_VALUE);
        int currentVersion = getApplicationVersion(context);
        if (registeredApplicationVersion != currentVersion) {
            Log.i(Constants.TAG, Constants.APPLICATION_VERSION_ERROR_MESSAGE);
            return null;
        }
        return geofenceLongitude;
    }

    public static String getGeofenceRadiusFromSharedPreferences(Context context) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        String geofenceRadius = sharedPreferences.getString(Constants.GEOFENCE_RADIUS, "");
        if (geofenceRadius.isEmpty()) {
            Log.i(Constants.TAG, Constants.GEOFENCE_RADIUS_ERROR_MESSAGE);
            return null;
        }
        int registeredApplicationVersion = sharedPreferences.getInt(Constants.APPLICATION_VERSION_PROPERTY, Integer.MIN_VALUE);
        int currentVersion = getApplicationVersion(context);
        if (registeredApplicationVersion != currentVersion) {
            Log.i(Constants.TAG, Constants.APPLICATION_VERSION_ERROR_MESSAGE);
            return null;
        }
        return geofenceRadius;
    }

    public static void setInformationIntoSharedPreferences(Context context, Location location, boolean geofenceStatus, String geofenceLatitude, String geofenceLongitude, String geofenceRadius) {
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        int currentVersion = getApplicationVersion(context);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(Constants.APPLICATION_VERSION_PROPERTY, currentVersion);
        Gson gson = new Gson();
        String lastLocation = gson.toJson(location);
        sharedPreferencesEditor.putString(Constants.LAST_LOCATION, lastLocation);
        sharedPreferencesEditor.putBoolean(Constants.GEOFENCE_STATUS, geofenceStatus);
        sharedPreferencesEditor.putString(Constants.GEOFENCE_LATITUDE, geofenceLatitude);
        sharedPreferencesEditor.putString(Constants.GEOFENCE_LONGITUDE, geofenceLongitude);
        sharedPreferencesEditor.putString(Constants.GEOFENCE_RADIUS, geofenceRadius);
        sharedPreferencesEditor.commit();
    }



}
