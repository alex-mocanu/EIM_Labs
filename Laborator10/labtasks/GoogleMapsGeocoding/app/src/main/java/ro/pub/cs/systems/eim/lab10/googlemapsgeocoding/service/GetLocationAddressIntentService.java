package ro.pub.cs.systems.eim.lab10.googlemapsgeocoding.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.List;

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

        List<Address> addressList = null;

        // TODO exercise 8
        // instantiate a Geocoder object
        // get the addresses list by calling the getFromLocation() method on Geocoder (supply latitude, longitude, Constants.NUMBER_OF_ADDRESSES)
        // handle IOException and IllegalArgumentException properly as well as no results supplied situation
        // iterate over the address list
        // concatenate all lines from each address (number of lines: getMaxAddressLineIndex(); specific line: getAddressLine()
        // call handleResult method with result (Constants.RESULT_SUCCESS, Constants.RESULT_FAILURE) and the address details / error message

        errorMessage = "Not implemented yet";
        handleResult(Constants.RESULT_FAILURE, errorMessage);
    }

    private void handleResult(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT, message);
        resultReceiver.send(resultCode, bundle);
    }
}
