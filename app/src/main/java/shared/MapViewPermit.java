package shared;

import android.app.Activity;

import libraries.android.ObjectTaskPermit;

public class MapViewPermit extends ObjectTaskPermit {

    public MapViewPermit(Activity context) {
        super(context);
        addAccessFineLocation();
        addInternet();
        addNetworkState();
        addWriteExternalStorage();
    }
}
