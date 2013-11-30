package raunimii.geotrack;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Activity for list view
 * @author Miika Raunio
 *
 */
public class ListActivity extends FragmentActivity{
	
	private ArrayList<TimeLocation> locArr = new ArrayList<TimeLocation>();
	
	/**
	 * Initiate the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    
	    // Get array of locations
	    locArr = getIntent().getParcelableArrayListExtra("locArr");
	    
	    // Get the ListView and setup an adapter
	    final ListView listview = (ListView) findViewById(R.id.listview);
	    final TimeLocationAdapter adapter = new TimeLocationAdapter(this, R.layout.list_item, locArr);
	    listview.setAdapter(adapter);
	    
	    // Set an listener for clicks on the list items.
	    listview.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l)
	        {
	        	// Return the associated object of the clicked item
	        	Intent resultIntent = new Intent();
	        	TimeLocation tl = (TimeLocation)a.getAdapter().getItem(i);
	        	resultIntent.putExtra("listReturn", tl);
	        	setResult(Activity.RESULT_OK, resultIntent);
	        	finish();
	        }
	    });
	    
	}

}
