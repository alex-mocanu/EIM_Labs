package ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.general.Constants;

public class GetLocationAddressIntentService extends IntentService {

    private ResultReceiver resultReceiver = null;

    public GetLocationAddressIntentService() {
        super(Constants.TAG);
        Log.i(Constants.TAG, "GetLocationAddressIntentService was created");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(Constants.TAG, "onHandleIntent() callback method has been invoked");

        String errorMessage = null;

        resultReceiver = intent.getParcelableExtra(Constants.RESULT_RECEIVER);
        if (resultReceiver == null) {
            errorMessage = "No result receiver was provided to handle the information";
        }

        Location location = intent.getParcelableExtra(Constants.LOCATION);
        if (location == null) {
            errorMessage = "No location data was provided";
        }

        if (errorMessage != null && !errorMessage.isEmpty()) {
            Log.e(Constants.TAG, "An exception has occurred: " + errorMessage);
            handleResult(Constants.RESULT_FAILURE, errorMessage);
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addressList = null;

        try {
            Log.i(Constants.TAG, "Started searching for the address...");
            addressList = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    Constants.NUMBER_OF_ADDRESSES
            );
        } catch (IOException ioException) {
            errorMessage = "The geocoding service is not available";
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "The latitude / longitude values that were provided were invalid " + location.getLatitude() + " / " + location.getLongitude();
            Log.e(Constants.TAG, "An exception has occurred: " + illegalArgumentException.getMessage());
            if (Constants.DEBUG) {
                illegalArgumentException.printStackTrace();
            }
        } finally {
            Log.i(Constants.TAG, "Finished searching for the address...");
        }

        if (errorMessage != null && !errorMessage.isEmpty()) {
            handleResult(Constants.RESULT_FAILURE, errorMessage);
            return;
        }

        if (addressList == null || addressList.isEmpty()) {
            errorMessage = "The geocoder could not find an address for the given latitude / longitude";
            Log.e(Constants.TAG, "An exception has occurred: " + errorMessage);
            handleResult(Constants.RESULT_FAILURE, errorMessage);
            return;
        }

        StringBuffer result = new StringBuffer();

        for (Address address: addressList) {
            for (int index = 0; index < address.getMaxAddressLineIndex(); index++) {
                result.append(address.getAddressLine(index) + System.getProperty("line.separator"));
            }
            result.append(System.getProperty("line.separator"));
        }
        Log.i(Constants.TAG, "There were " + addressList.size() + " addresses found");
        handleResult(Constants.RESULT_SUCCESS, result.toString());

    }

    private void handleResult(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT, message);
        resultReceiver.send(resultCode, bundle);
    }
}
