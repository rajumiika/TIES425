package raunimii.geotrack;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Activity for the main menu of GeoTrack
 * @author Miika Raunio
 *
 */
public class MainActivity extends FragmentActivity implements LocationListener{
	
	private Intent splash;
	private final int splashCode = 0;
	private boolean acquireFirst = true;
	LocationManager lm;
	
	/**
	 * Set up the location listener and show splash screen
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
    	setContentView(R.layout.activity_main);
    	if (acquireFirst){
        	splash = new Intent("splash");
        	this.startActivityForResult(splash, splashCode);
        }
    }
    
    /**
     * Close the splash screen, when a location change has been detected.
     */
    public void onLocationChanged(final Location location) {
		if (acquireFirst){
			acquireFirst = false;
			finishActivity(splashCode);
        }
    }
    
    /**
     * Open the map view.
     * @param view Something
     */
    public void viewMap(View view)
	{
		Intent mapActivity = new Intent("map");
		startActivity(mapActivity);
	}
    
    /**
	 * Stop requesting for updates, when shutting down.
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		lm.removeUpdates(this);
		lm=null;
	}
	
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
}