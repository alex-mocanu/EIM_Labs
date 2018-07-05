package ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.view;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import ro.pub.cs.systems.eim.lab10.R;
import ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.general.Constants;
import ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.service.GetLocationAddressIntentService;

public class GoogleMapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap googleMap = null;
    private GoogleApiClient googleApiClient = null;

    private Location lastLocation = null;

    private EditText latitudeEditText = null;
    private EditText longitudeEditText = null;
    private Button navigateToLocationButton = null;
    private Button getLocationAddressButton = null;

    private boolean getAddressLocationStatus = false;

    private TextView addressTextView  = null;

    private AddressResultReceiver addressResultReceiver = null;
    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle bundle) {
            String address = bundle.getString(Constants.RESULT);
            addressTextView.setText(address);

            switch (resultCode) {
                case Constants.RESULT_SUCCESS:
                    Toast.makeText(getApplicationContext(), "An address was found", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.RESULT_FAILURE:
                    Toast.makeText(getApplicationContext(), "An address could not be found", Toast.LENGTH_SHORT).show();
                    break;
            }

            getAddressLocationStatus = false;
            getLocationAddressButton.setEnabled(true);
        }
    }

    private NavigateToLocationButtonListener navigateToLocationButtonListener = new NavigateToLocationButtonListener();
    private class NavigateToLocationButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String latitudeContent = latitudeEditText.getText().toString();
            String longitudeContent = longitudeEditText.getText().toString();

            if (latitudeContent == null || latitudeContent.isEmpty()
                    || longitudeContent == null || longitudeContent.isEmpty()) {
                Toast.makeText(getApplicationContext(), "GPS coordinates should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            double latitudeValue = Double.parseDouble(latitudeContent);
            double longitudeValue = Double.parseDouble(longitudeContent);
            navigateToLocation(latitudeValue, longitudeValue);
        }

    }

    private void navigateToLocation(double latitude, double longitude) {

        latitudeEditText.setText(String.valueOf(latitude));
        longitudeEditText.setText(String.valueOf(longitude));
        if (lastLocation == null) {
            lastLocation = new Location("");
        }
        lastLocation.setLatitude(latitude);
        lastLocation.setLongitude(longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(Constants.CAMERA_ZOOM)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void navigateToLocation(Location location) {
        if (location != null) {
            navigateToLocation(location.getLatitude(), location.getLongitude());
        }
    }

    private GetLocationAddressButtonListener getLocationAddressButtonListener = new GetLocationAddressButtonListener();
    private class GetLocationAddressButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            if (googleApiClient != null && googleApiClient.isConnected() && lastLocation != null) {
                Log.i(Constants.TAG, "GetLocationAddressButonListener -> onClick()");
                startIntentService();
                getAddressLocationStatus = true;
                getLocationAddressButton.setEnabled(false);
            }
        }

    }

    private void startIntentService() {
        Log.i(Constants.TAG, "startIntentService()");
        Intent intent = new Intent(this, GetLocationAddressIntentService.class);
        intent.putExtra(Constants.RESULT_RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LOCATION, lastLocation);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        Log.i(Constants.TAG, "onCreate() callback method was invoked");

        addressResultReceiver = new AddressResultReceiver(new Handler());

        latitudeEditText = (EditText)findViewById(R.id.latitude_edit_text);
        longitudeEditText = (EditText)findViewById(R.id.longitude_edit_text);
        navigateToLocationButton = (Button)findViewById(R.id.navigate_to_location_button);
        navigateToLocationButton.setOnClickListener(navigateToLocationButtonListener);
        getLocationAddressButton = (Button)findViewById(R.id.get_location_address_button);
        getLocationAddressButton.setOnClickListener(getLocationAddressButtonListener);
        addressTextView = (TextView)findViewById(R.id.address_text_view);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (savedInstanceState != null) {
            restoreValues(savedInstanceState);
            if (getAddressLocationStatus) {
                getLocationAddressButton.setEnabled(false);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Constants.TAG, "onStart() callback method was invoked");
        if (googleMap == null) {
            ((MapFragment)getFragmentManager().findFragmentById(R.id.google_map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap readyGoogleMap) {
                    googleMap = readyGoogleMap;
                }
            });
        }
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        Log.i(Constants.TAG, "onStop() callback method was invoked");
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "onDestroy() callback method was invoked");
        googleApiClient = null;
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(Constants.TAG, "onSaveInstanceState() callback method was invoked");
        saveValues(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(Constants.TAG, "onRestoreInstanceState() callback method was invoked");
        restoreValues(savedInstanceState);
        if (getAddressLocationStatus) {
            getLocationAddressButton.setEnabled(false);
        }
    }

    private void saveValues(Bundle state) {
        state.putParcelable(Constants.LAST_LOCATION, lastLocation);
        state.putBoolean(Constants.GET_ADDRESS_LOCATION_STATUS, getAddressLocationStatus);
    }

    private void restoreValues(Bundle state) {
        if (state.containsKey(Constants.LAST_LOCATION)) {
            lastLocation = state.getParcelable(Constants.LAST_LOCATION);
        }
        if (state.containsKey(Constants.GET_ADDRESS_LOCATION_STATUS)) {
            getAddressLocationStatus = state.getBoolean(Constants.GET_ADDRESS_LOCATION_STATUS);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(Constants.TAG, "onConnected() callback method has been invoked");
        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                navigateToLocation(lastLocation);

                if (!Geocoder.isPresent()) {
                    Toast.makeText(getApplicationContext(), "The geocoding service is not available", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (getAddressLocationStatus) {
                    startIntentService();
                }
            }
        } catch (SecurityException securityException) {
            Log.e(Constants.TAG, securityException.getMessage());
            if (Constants.DEBUG) {
                securityException.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(Constants.TAG, "onConnectionSuspended() callback method has been invoked with cause " + cause);
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Constants.TAG, "onConnectionFailed() callback method has been invoked");
    }
}
