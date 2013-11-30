package raunimii.geotrack;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * A service for LocationListener.
 * @author Miika Raunio
 *
 */
public class LocationService extends Service implements LocationListener{

	private LocationManager lm;
	
	/**
	 * When the service starts
	 */
	public int onStartCommand(final Intent intent, final int flags, final int startId) {

		// Start or stop listening
        if (intent.getAction().equals("startListening")) {
            lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
        }
        else {
            if (intent.getAction().equals("stopListening")) {
                lm.removeUpdates(this);
                lm = null;
            }
        }

        return START_STICKY;
	}
	
	/**
	 * Send captured location in a broadcast
	 */
	@Override
	public void onLocationChanged(Location loc) {
		TimeLocation tl = new TimeLocation(Calendar.getInstance(), loc);
		Intent intent = new Intent("locationChanged");
		intent.putExtra("timeLocation", tl);
        sendBroadcast(intent);
	}
	
	/*
	 * Bunch of unwritten method stubs
	 */
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
