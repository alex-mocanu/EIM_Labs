package ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import ro.pub.cs.systems.eim.lab10.R;
import ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.general.Constants;
import ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.view.GoogleMapsGeofenceEventActivity;

public class GeofenceTrackerIntentService extends IntentService {

    public GeofenceTrackerIntentService() {
        super(Constants.TAG);
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Log.i(Constants.TAG, "onHandleIntent() callback method has been invoked");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.i(Constants.TAG, "Geofencing event has an error: " + geofencingEvent.getErrorCode());
            String errorMessage = null;
            switch(geofencingEvent.getErrorCode()) {
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    errorMessage = Constants.GEOFENCE_NOT_AVAILABLE_ERROR;
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    errorMessage = Constants.GEOFENCE_TOO_MANY_GEOFENCES_ERROR;
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    errorMessage = Constants.GEOFENCE_TOO_MANY_PENDING_INTENTS_ERROR;
                    break;
                default:
                    errorMessage = Constants.GEOFENCE_UNKNOWN_ERROR;
                    break;
            }
            Log.e(Constants.TAG, "An exception has occurred: " + errorMessage);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            StringBuffer transitionStringDetails = null;
            switch(geofenceTransition) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    transitionStringDetails = new StringBuffer(Constants.GEOFENCE_TRANSITION_ENTER);
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    transitionStringDetails = new StringBuffer(Constants.GEOFENCE_TRANSITION_EXIT);
                    break;
                default:
                    transitionStringDetails = new StringBuffer(Constants.GEOFENCE_TRANSITION_UNKNOWN);
                    break;
            }
            transitionStringDetails.append(": ");
            for (Geofence geofence: triggeringGeofences) {
                transitionStringDetails.append(geofence.getRequestId() + ", ");
            }
            String transitionString = transitionStringDetails.toString();
            if (transitionString.endsWith(", ")) {
                transitionString = transitionString.substring(0 ,transitionString.length() - 2);
            }
            sendNotification(transitionString);
            Log.i(Constants.TAG, "The geofence transition has been processed: " + transitionString);
        } else {
            Log.e(Constants.TAG, "An exception has occurred: " + Constants.GEOFENCE_TRANSITION_UNKNOWN + " " + geofenceTransition);
        }

    }

    private void sendNotification(String notificationDetails) {
        Intent notificationIntent = new Intent(getApplicationContext(), GoogleMapsGeofenceEventActivity.class);
        notificationIntent.putExtra(Constants.NOTIFICATION_DETAILS, notificationDetails);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GoogleMapsGeofenceEventActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle(Constants.GEOFENCE_TRANSITION_EVENT)
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
