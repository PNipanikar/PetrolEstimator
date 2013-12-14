    package com.adtl;  
      
    import java.util.ArrayList;  
    import java.util.List;  
      
    import com.google.android.maps.GeoPoint;  
    import com.google.android.maps.ItemizedOverlay;  
    import com.google.android.maps.MapActivity;  
    import com.google.android.maps.MapController;  
    import com.google.android.maps.MapView;  
    import com.google.android.maps.Overlay;  
    import com.google.android.maps.OverlayItem;  
      
    import android.graphics.drawable.Drawable;  
    import android.os.Bundle;  
    import android.widget.Toast;  
      
    public class hotel extends MapActivity {  
    MapView mapView; 
    MapController mc;  
    List<Overlay> mapOverlays;  
    Drawable drawable;  
    MyItemizedOverlay itemizedOverlay;  
      
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.showhotels);  
      
    // Capture  mapView through their layout resources  
    mapView = (MapView) findViewById(R.id.maphotel);  
    //Get the ZoomControls from the MapView  
    mapView.setBuiltInZoomControls(true);  
      
    // Instantiate the new fields  
    mapOverlays = mapView.getOverlays();  
    // Mark icon  
    drawable = this.getResources().getDrawable(R.drawable.petrol);  
    itemizedOverlay = new MyItemizedOverlay(drawable);  
      
    // Define two locations Latitude and Longitude Values  
    Double lat = 18.5361078 * 1E6;  
    Double lng =  73.8495619 * 1E6;  
      
    Double lat2 =  18.527231 * 1E6;  
    Double lng2 = 73.851272* 1E6;  
    
    Double lat3 = 18.5308225 * 1E6;  
    Double lng3 = 73.8474647* 1E6;  
    
    Double lat4 = 18.5204303 * 1E6;  
    Double lng4 = 73.8567436* 1E6;  
      
    // GeoPoint 1  
    GeoPoint point = new GeoPoint(lat.intValue(), lng.intValue());  
    OverlayItem overlayitem = new OverlayItem(point, "Pride Hotel Pune",  
     "University road");  
      
    // GeoPoint 2  
    GeoPoint point2 = new GeoPoint(lat2.intValue(), lng2.intValue());  
    OverlayItem overlayitem2 = new OverlayItem(point2,  
    "Modern Cafe shivaji nagar", "pune");  
    
    GeoPoint point3 = new GeoPoint(lat3.intValue(), lng3.intValue());  
    OverlayItem overlayitem3 = new OverlayItem(point3,  
    "Shumbham hotel,shivaji nagar", "pune");  
    
    GeoPoint point4 = new GeoPoint(lat.intValue(), lng.intValue());  
    OverlayItem overlayitem4 = new OverlayItem(point4, "Wadeshwar Hotel,FC Road",  
     "pune");  
      
    // add OverlayItems  
    itemizedOverlay.addOverlay(overlayitem);  
    itemizedOverlay.addOverlay(overlayitem2); 
    itemizedOverlay.addOverlay(overlayitem3);
    itemizedOverlay.addOverlay(overlayitem4);
    // add itemizedOverlay in mapview  
    mapOverlays.add(itemizedOverlay);  
    //move GeoPoint 1 to center  
    mc = mapView.getController();  
    mc.setCenter(point);  
    mc.setZoom(14);  
    }  
      
    @Override  
    protected boolean isRouteDisplayed() {  
    // TODO Auto-generated method stub  
    return false;  
    }  
      
    //Customized  MyItemizedOverlay class  
    class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {  
    //Define an OverlayItem ArrayList, in which we'll put each of  
    // the OverlayItem objects we want on our map  
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();  
      
    public MyItemizedOverlay(Drawable defaultMarker) {  
    // Wrap this around our defaultMarker  
    super(boundCenterBottom(defaultMarker));  
    // TODO Auto-generated constructor stub  
    }  
      
    // Get ArrayList items  
    @Override  
    protected OverlayItem createItem(int i) {  
    // TODO Auto-generated method stub  
    return mOverlays.get(i);  
      
    }  
      
    // return OverlayItem size  
    @Override  
    public int size() {  
    // TODO Auto-generated method stub  
    return mOverlays.size();  
    }  
      
    // Show the locations message  
    @Override  
    protected boolean onTap(int index) {  
    OverlayItem item = mOverlays.get(index);  
    // Title  
    String title = item.getTitle();  
    // Snippet  
    String snippet = item.getSnippet();  
    Toast.makeText(hotel.this, title + "\n" + snippet,  
    Toast.LENGTH_LONG).show();  
    return true;  
    }  
      
    // Define a method in order to add new OverlayItems to our ArrayList  
    public void addOverlay(OverlayItem overlay) {  
    // add OverlayItems  
    mOverlays.add(overlay);  
    // populate  
    populate();  
    }  
    }  
    }  
