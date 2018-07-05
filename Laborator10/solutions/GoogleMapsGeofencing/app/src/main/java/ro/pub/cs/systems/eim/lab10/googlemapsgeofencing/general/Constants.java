package ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.general;

public interface Constants {

    public static String TAG = "[GoogleMapsGeofencing]";

    public static boolean DEBUG = true;

    public static int CAMERA_ZOOM = 12;

    public static long LOCATION_REQUEST_INTERVAL = 10000;
    public static long LOCATION_REQUEST_FASTEST_INTERVAL = 5000;

    public static String LAST_LOCATION = "LastLocation";
    public static String LAST_LOCATION_ERROR_MESSAGE = "Last Location is empty";
    public static String GEOFENCE_STATUS = "GeofenceStatus";
    public static String GEOFENCE_LATITUDE = "GeofenceLatitude";
    public static String GEOFENCE_LATITUDE_ERROR_MESSAGE = "Geofence Latitude is empty";
    public static String GEOFENCE_LONGITUDE = "GeofenceLongitude";
    public static String GEOFENCE_LONGITUDE_ERROR_MESSAGE = "Geofence Longitude is empty";
    public static String GEOFENCE_RADIUS = "GeofenceRadius";
    public static String GEOFENCE_RADIUS_ERROR_MESSAGE = "Geofence Radius is empty";
    public static String APPLICATION_VERSION_PROPERTY = "ApplicationVersion";

    public static char FIRST_LETTER = 'a';
    public static int ALPHABET_LENGTH = 26;
    public static int GEOFENCE_IDENTIFIER_LENGTH = 5;

    public static long GEOFENCE_EXPIRATION_IN_HOURS = 1;
    public static long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    public static String GEOFENCE_TRANSITION_ENTER = "The user has entered the geofence area";
    public static String GEOFENCE_TRANSITION_EXIT = "The user has exited the geofence area";
    public static String GEOFENCE_TRANSITION_UNKNOWN = "Unknown geofence transition";

    public static String GEOFENCE_NOT_AVAILABLE_ERROR = "Geofence not available";
    public static String GEOFENCE_TOO_MANY_GEOFENCES_ERROR = "There are too many geofences";
    public static String GEOFENCE_TOO_MANY_PENDING_INTENTS_ERROR = "There are too many pending intents";
    public static String GEOFENCE_UNKNOWN_ERROR = "Unknown geofence error";

    public static String GEOFENCE_TRANSITION_EVENT = "Geofence Transition Event";

    public static String NOTIFICATION_DETAILS = "Notification Details";
    public static String APPLICATION_VERSION_ERROR_MESSAGE = "The application version was changed.";

}
