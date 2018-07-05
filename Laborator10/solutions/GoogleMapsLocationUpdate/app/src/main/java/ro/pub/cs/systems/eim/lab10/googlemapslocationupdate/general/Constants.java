package ro.pub.cs.systems.eim.lab10.googlemapslocationupdate.general;

public interface Constants {

    public static String TAG = "[GoogleMapsLocation]";

    public static boolean DEBUG = true;

    public static int NORMAL_MAP_TYPE = 0;
    public static int SATELLITE_MAP_TYPE = 1;
    public static int TERRAIN_MAP_TYPE = 2;
    public static int HYBRID_MAP_TYPE = 3;

    public static int CAMERA_ZOOM = 12;

    public static long LOCATION_REQUEST_INTERVAL = 10000;
    public static long LOCATION_REQUEST_FASTEST_INTERVAL = 10000;

    public static String LOCATION_UPDATES_STATUS = "LocationUpdatesStatus";
    public static String LAST_LOCATION = "LastLocation";

}
