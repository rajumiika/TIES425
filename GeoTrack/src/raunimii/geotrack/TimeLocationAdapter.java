package raunimii.geotrack;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter getting data from TimeLocation object to a list view item.
 * @author Miika
 *
 */
public class TimeLocationAdapter extends ArrayAdapter<TimeLocation>{
		
	Context context; 
    int layoutResourceId;    
    ArrayList<TimeLocation> data;
    
    /**
     * Constructor
     * @param context
     * @param layoutResourceId
     * @param data
     */
    public TimeLocationAdapter(Context context, int layoutResourceId, ArrayList<TimeLocation> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    /**
     * Get the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TimeLocationHolder holder;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new TimeLocationHolder();
            holder.location = (TextView)row.findViewById(R.id.location);
            holder.time = (TextView)row.findViewById(R.id.time);
            
            row.setTag(holder);
        }
        else
        {
            holder = (TimeLocationHolder)row.getTag();
        }
        
        TimeLocation tl = data.get(position);
        holder.time.setText(tl.toTimeString());
        holder.location.setText(tl.toLocationString());
        
        return row;
    }
    
    static class TimeLocationHolder
    {
    	TextView time;
    	TextView location;
    }
}
