package ro.pub.cs.systems.eim.lab10.googlemapplaces.general;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Utilities {

    public static float getDefaultMarker(int position) {
        switch (position) {
            case Constants.MAGENTA_POSITION:
                return BitmapDescriptorFactory.HUE_MAGENTA;
            case Constants.VIOLET_POSITION:
                return BitmapDescriptorFactory.HUE_VIOLET;
            case Constants.ORANGE_POSITION:
                return BitmapDescriptorFactory.HUE_ORANGE;
            case Constants.RED_POSITION:
                return BitmapDescriptorFactory.HUE_RED;
            case Constants.BLUE_POSITION:
                return BitmapDescriptorFactory.HUE_BLUE;
            case Constants.GREEN_POSITION:
                return BitmapDescriptorFactory.HUE_GREEN;
            case Constants.AZURE_POSITION:
                return BitmapDescriptorFactory.HUE_AZURE;
            case Constants.ROSE_POSITION:
                return BitmapDescriptorFactory.HUE_ROSE;
            case Constants.CYAN_POSITION:
                return BitmapDescriptorFactory.HUE_CYAN;
            case Constants.YELLOW_POSITION:
                return BitmapDescriptorFactory.HUE_YELLOW;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

}
