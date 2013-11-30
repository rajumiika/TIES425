package raunimii.geotrack;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Activity for the map view
 * @author Miika Raunio
 *
 */
public class MapActivity extends FragmentActivity implements LocationListener{
 
private GoogleMap map;
private ArrayList<TimeLocation> locArr = new ArrayList<TimeLocation>();
private LocationManager lm;
private final int listCode = 1;
	
	/**
	 * Setup the activity
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
        setContentView(R.layout.activity_map);
        map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
    }
	
	/**
	 * Center to the location that was clicked on the list view.
	 */
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
			case (listCode) : {
				if (resultCode == Activity.RESULT_OK) {
					
				      TimeLocation tl = data.getParcelableExtra("listReturn");
				      LatLngBounds.Builder builder = new LatLngBounds.Builder();
				      builder.include(new LatLng(tl.location.getLatitude(), tl.location.getLongitude()));
				      LatLngBounds bounds = builder.build();
				      CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 0);
				      CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
				      map.moveCamera(update);
				      map.moveCamera(zoom);
				      break;
				}
			} 
		} 
	}
	
	
	/**
	 * Add a new location to array.
	 */
	@Override
    public void onLocationChanged(final Location location) {
		Location loc = map.getMyLocation();
		if (loc != null){
			TimeLocation tl = new TimeLocation(Calendar.getInstance(), loc);
			locArr.add(tl);
			map.clear();
			setMarkers();
		}
    }
	
	/**
	 * Open the list view
	 * @param view Something
	 */
	public void viewList(View view)
	{
		Intent listActivity = new Intent("list");
		listActivity.putParcelableArrayListExtra("locArr", locArr);
		startActivityForResult(listActivity, listCode);
	}
	
	/**
	 * Sets the markers based on the contents of locArr.
	 */
	public void setMarkers(){
		TimeLocation tl;
		LatLng ll;
		MarkerOptions mark = new MarkerOptions();
		for (int i=0; i<locArr.size();i++){
			tl = (TimeLocation)locArr.get(i);
			ll = new LatLng(tl.location.getLatitude(), tl.location.getLongitude());
			map.addMarker(mark.position(ll).title(tl.toString()));
		}
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