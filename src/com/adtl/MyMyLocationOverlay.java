package com.adtl;



import android.content.Context;
import android.widget.Toast;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

// This class subclasses (extends) MyLocationOverlay so that 
// we can override its dispatchTap method
// to handle tap events on the present location dot.

public class MyMyLocationOverlay extends MyLocationOverlay {

    private Context context;

    public MyMyLocationOverlay(Context context, MapView mapView) {
        super(context, mapView);
        this.context = context;   
    }
        
    // Add stub to Override the dispatchTap() method. Will fill in below. 
    
    @Override
    protected boolean dispatchTap(){
        // More to add later
        return true;
    }
}
