package raunimii.geotrack;

import java.text.DecimalFormat;
import java.util.Calendar;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * An object to contain the pair of time and location when a new location is detected.
 * @author Miika Raunio
 *
 */
public class TimeLocation extends Object implements Parcelable{
	
	public Location location;
	public Calendar time;
	
	/**
	 * Constructor to use with Calendar and Location objects.
	 * @param t Contains the current time
	 * @param loc Contains the current location
	 */
	public TimeLocation(Calendar t, Location loc) {
	    location = loc;
	    time = t;
	}
	
	/**
	 * Constructor for for initializing TimeLocation from Parcel
	 * @param in
	 */
	public TimeLocation(Parcel in) {
		time = (Calendar)in.readSerializable();
		location = (Location)in.readParcelable(Location.class.getClassLoader());
	}

	/**
	 * Parse the location to human-readable format.
	 */
	public String toString(){
		String str = toTimeString() + " " + toLocationString();
		return str;
	}
	
	/**
	 * Parse the time to human-readable format.
	 */
	public String toTimeString(){
		String str = time.get(time.HOUR_OF_DAY) + ":" + time.get(time.MINUTE) + ":" + time.get(time.SECOND);
		return str;
	}
	
	/**
	 * Parse the object to human-readable format.
	 */
	public String toLocationString(){
		DecimalFormat df = new DecimalFormat("#.###");
		String str = "Lat/Long: " + df.format(location.getLatitude()) + " " + df.format(location.getLongitude());
		return str;
	}

	/**
	 * Required method of the interface. Do something with this, maybe.
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Write the contents of this instance into a parcel. For enabling the passing of ListArray<TimeLocation> to an Activity.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(time);
		dest.writeParcelable(location, PARCELABLE_WRITE_RETURN_VALUE);
		
	}
	
	/**
	 * Necessary for making the class parcelable.
	 */
	public static final Parcelable.Creator<TimeLocation> CREATOR = new Parcelable.Creator<TimeLocation>() {
		public TimeLocation createFromParcel(Parcel in) {
		    return new TimeLocation(in);
		}
		
		public TimeLocation[] newArray(int size) {
		    return new TimeLocation[size];
		}
		};
}