package com.adtl;
import java.util.List;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapMe extends MapActivity implements LocationListener {
	 private MyMyLocationOverlay myLocationOverlay;
private static double lat;
private static double lon;
private MapController mapControl;
private MapView mapView;
LocationManager locman;
    Location loc;
    String provider = LocationManager.GPS_PROVIDER;
    String TAG = "GPStest";
Bundle locBundle;
int numberSats = -1;
float satAccuracy = 2000;
private float bearing;
private double altitude;
private float speed;
private String currentProvider;
long GPSupdateInterval;         
float GPSmoveInterval;          
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.mapme); 
        GPSupdateInterval = 5000;
        GPSmoveInterval = 1;
        locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        final GpsStatus.Listener onGpsStatusChange = new GpsStatus.Listener(){
            public void onGpsStatusChanged(int event){
                switch(event){
                    case GpsStatus.GPS_EVENT_STARTED:
                    break;
                    case GpsStatus.GPS_EVENT_FIRST_FIX:    
                            Toast.makeText(MapMe.this, "GPS has 1st fix", Toast.LENGTH_LONG).show();
                    break;
                    case GpsStatus.GPS_EVENT_STOPPED:
                          
                    break;
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                            
                    break;
                }
                GpsStatus status = locman.getGpsStatus(null);
                
                Iterable<GpsSatellite> satlist = status.getSatellites();
            }
        };

        locman.addGpsStatusListener(onGpsStatusChange);
        locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);  
        Log.i(TAG, locman.toString());
            

        mapView = (MapView) findViewById(R.id.mv2);
        mapView.setSatellite(false);
        mapView.setTraffic(false);
        mapView.setBuiltInZoomControls(true);   
        int maxZoom = mapView.getMaxZoomLevel();
        int initZoom = (int)(0.95*(double)maxZoom);
        mapControl = mapView.getController();
        mapControl.setZoom(initZoom);
        
        List<Overlay> overlays = mapView.getOverlays();
        myLocationOverlay = new MyMyLocationOverlay(this,mapView);
        overlays.add(myLocationOverlay);   
    }



public boolean onKeyDown(int keyCode, KeyEvent e){
        if(keyCode == KeyEvent.KEYCODE_S){
            mapView.setSatellite(!mapView.isSatellite());
            return true;
    } else if(keyCode == KeyEvent.KEYCODE_T){
            mapView.setTraffic(!mapView.isTraffic());
            centerOnLocation();
    }
    return(super.onKeyDown(keyCode, e));
}


@Override
protected boolean isRouteDisplayed() {
    return true; 
}

// Required method since class implements LocationListener interface
@Override
public void onLocationChanged(Location location) {
    // Called when location has changed
    centerOnLocation();
}

// Required method since class implements LocationListener interface
@Override
public void onProviderDisabled(String provider) {
    // Called when user disables the location provider. If 
    // requestLocationUpdates is called on an already disabled 
    // provider, this method is called immediately.
	locman.removeUpdates(this);
    	
}

// Required method since class implements LocationListener interface
@Override
public void onProviderEnabled(String provider) {
    // Called when the user enables the location provider
	locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);
    
}

// Required method since class implements LocationListener interface
@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
    // Called when the provider status changes. This method is called 
    // when a provider is unable to fetch a location or if the provider 
    // has recently become available after a period of unavailability.
    
    centerOnLocation();	
}

// Method to query phone location and center map on that location
private void centerOnLocation() {
	loc = locman.getLastKnownLocation(provider);
	if(loc != null){
        lat = loc.getLatitude();
        lon = loc.getLongitude();
        GeoPoint newPoint = new GeoPoint((int)(lat*1e6),(int)(lon*1e6)); 	   
        mapControl.animateTo(newPoint);
        getSatelliteData();
    }
}

// Method to determine the number of satellites contributing to the fix and
// various other quantities.

public void getSatelliteData(){
	if(loc != null){
		
        // Determine number of satellites used for the fix
        locBundle = loc.getExtras();
        if(locBundle != null){ 
            numberSats = locBundle.getInt("satellites",-1);
        }
   	    
       // Following return 0 if the corresponding boolean (e.g., hasAccuracy) are false.
        satAccuracy = loc.getAccuracy();
        bearing = loc.getBearing();
        altitude = loc.getAltitude();
        speed = loc.getSpeed();		
    }
}
// OnPause() and onResume() methods to handle when app is forced to background by another
// process and then resumed. Generally should release resources not needed while in background
// and restore when resumed. For example, in the following locman.removeUpdates(this) when 
// pausing removes the request for GPS updates when MapMe goes into the background. This
// permits the GPS engine to shut down (if it is not being used by another program), which saves
// a lot of power. If MapMe is resumed (moved from background to foreground), the onResume()
// method resumes GPS update requests through the locman.requestLocationUpdates() method.
public void onPause() {
    super.onPause();
    Log.i(TAG,"******  MapMe is pausing: Removing GPS update requests to save power");
    myLocationOverlay.disableCompass();
    myLocationOverlay.disableMyLocation();
    locman.removeUpdates(this);
}
public void onResume(){
    super.onResume();
    Log.i(TAG,"******  MapMe is restarting: Resuming GPS update requests");	   
    locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);	
    myLocationOverlay.enableCompass();
    myLocationOverlay.enableMyLocation();
}
}
