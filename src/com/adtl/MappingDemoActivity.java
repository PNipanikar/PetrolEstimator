package com.adtl;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;



import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
public class MappingDemoActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	public String rest,sDistance;
	 public static double lat1,lat2;
     public static double lon1,lon2;
     public InputStream is;
     EditText e,t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);
        // Add Click listeners for all buttons
        View firstButton = findViewById(R.id.geocode_button);
        firstButton.setOnClickListener(this);
        
        View thirdButton = findViewById(R.id.presentLocation_button);
        thirdButton.setOnClickListener(this);
    }
       
    public static JSONObject getLocationInfo(String placeName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
        	placeName = placeName.replaceAll(" ","%20");    
        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + placeName + "&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        stringBuilder = new StringBuilder();
            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }
    public static boolean getLatLong1(JSONObject jsonObject) {
        try {
            lon1 = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");
            lat1 = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
    public static boolean getLatLong2(JSONObject jsonObject) {

        try {
            lon2 = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");
            lat2 = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");
        } catch (JSONException e) {
            return false;
        }  
        return true;
    }
    public void finishUp(){
        finish();
     }
	public void onClick(View v) {
		switch(v.getId()){
        case R.id.geocode_button:
        	 // Following adapted from Conder and Darcey, pp.321 ff.		
            EditText sourceText = (EditText) findViewById(R.id.geocode_input);	
            EditText destText=(EditText)findViewById(R.id.eT1);
            String sourceName = sourceText.getText().toString();
            String destName=destText.getText().toString();
            // Break from execution if the user has not entered anything in the field
            if(sourceName.compareTo("")==0) break;
            getLatLong1(getLocationInfo(sourceName));
            getLatLong2(getLocationInfo(destName));
            {  
           StringBuilder urlString = new StringBuilder();
           urlString.append("http://maps.googleapis.com/maps/api/distancematrix/json?");
           urlString.append("origins=");//from
           urlString.append(Double.toString(lat1));
           urlString.append(",");
           urlString.append(Double.toString(lon1));
           urlString.append("&destinations=");
           urlString.append(Double.toString(lat2));
           urlString.append(",");
           urlString.append(Double.toString(lon2));
           urlString.append("&mode=driving&sensor=true");
           Log.d("xxx","URL="+urlString.toString());
           Toast.makeText(MappingDemoActivity.this, urlString, Toast.LENGTH_LONG).show();
           try{
               HttpClient httpclient = new DefaultHttpClient();
               HttpPost httppost = new HttpPost(urlString.toString());
               HttpResponse response = httpclient.execute(httppost);
               HttpEntity entity = response.getEntity();
               is = entity.getContent();
               
       }catch(Exception e){
               Log.e("log_tag", "Error in http connection "+e.toString());
       }
       try{
           BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
           StringBuilder sb = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
                   sb.append(line + "\n");
           }
           is.close();
            rest = sb.toString();
            Toast.makeText(MappingDemoActivity.this,rest,Toast.LENGTH_LONG).show();
            System.out.print(rest);
       }catch(Exception e){
           Log.e("log_tag", "Error converting result "+e.toString());
       }
   		        //Sortout JSONresponse 
           //JSONObject object;
   		try {
   			JSONObject object = new JSONObject(rest);	
   		    JSONArray rows = object.getJSONArray("rows");
   		        //Log.d("JSON","array: "+array.toString());

   		    //Routes is a combination of objects and arrays
   		    JSONObject rowsobject = rows.getJSONObject(0);
   		       // Log.d("JSON","routes: "+routes.toString());
   		    //String summary = routes.getString("summary");
   		        //Log.d("JSON","summary: "+summary);
   		    JSONArray elements = rowsobject.getJSONArray("elements");
   		        //Log.d("JSON","legs: "+legs.toString());
   		    JSONObject elementsobject = elements.getJSONObject(0);
   		            //Log.d("JSON","steps: "+steps.toString());
   		    JSONObject distance = elementsobject.getJSONObject("distance");
   		        //Log.d("JSON","distance: "+distance.toString());

   		            sDistance = distance.getString("text");
   		            int iDistance = distance.getInt("value");        
   		            Toast.makeText(MappingDemoActivity.this, sDistance, Toast.LENGTH_LONG).show();
   		            
   		        
   		         Bundle bundle=new Bundle();
             	bundle.putInt("param",iDistance);
             	Intent j = new Intent(v.getContext(), ShowLabel.class);
             	j.putExtras(bundle);
             	//j.putExtras(bundle);
            // ShowTheMap.putLatLong(lat2,lon2);
             startActivity(j);
   		         
   		} catch (JSONException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}        
          }
          /*  int numberOptions = 5;
            String [] optionArray = new String[numberOptions];
            Geocoder gcoder = new Geocoder(this);  
              
            // Note that the Geocoder uses synchronous network access, so in a serious application
            // it would be best to put it on a background thread to prevent blocking the main UI if network
            // access is slow. Here we are just giving an example of how to use it so, for simplicity, we
            // don't put it on a separate thread.
                                
            try{
                List<Address> results = gcoder.getFromLocationName(placeName,numberOptions);
                Iterator<Address> locations = results.iterator();
                String raw = "\nRaw String:\n";
                String country;
                int opCount = 0;
                while(locations.hasNext()){
                    Address location = locations.next();
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    country = location.getCountryName();
                    if(country == null) {
                        country = "";
                    } else {
                        country =  ", "+country;
                    }
                    raw += location+"\n";
                    optionArray[opCount] = location.getAddressLine(0)+", "+location.getAddressLine(1)+country+"\n";
                    opCount ++;
                }
                Log.i("Location-List", raw);
                Log.i("Location-List","\nOptions:\n");
                for(int i=0; i<opCount; i++){
                    Log.i("Location-List","("+(i+1)+") "+optionArray[i]);
                }
    					
            } catch (IOException e){
                Log.e("Geocoder", "I/O Failure; is network available?",e);
            }			
            Intent j = new Intent(this, ShowTheMap.class);
            ShowTheMap.putLatLong(lat,lon);
            startActivity(j);*/
        break;
       case R.id.presentLocation_button:
            Log.i("Button","Button 3 pushed");
            Intent m = new Intent(this, MapMe.class);
            startActivity(m);
        break;	
	}
		/* double d1;
	         d1=Double.parseDouble(sDistance);
	      try{
			File myFile = new File("/sdcard/average.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			aDataRow = myReader.readLine();
			 String rec[]=aDataRow.split("[,]");
           String val1 = rec[0];
           String val2 = rec[1];
           myReader.close();
           File myFile1 = new File("/sdcard/petrolq.txt");
			FileInputStream fIn1= new FileInputStream(myFile1);
			BufferedReader myReader1 = new BufferedReader(
					new InputStreamReader(fIn1));
			String aDataRow1 = "";
			String aBuffer1 = "";
			aDataRow1 = myReader1.readLine();
			String rec1[]=aDataRow1.split("[,]");
          String val11 = rec1[0];
          String val21 = rec1[1];
          myReader1.close();
           double r=Double.parseDouble(rec[1]);
           double r1=Double.parseDouble(rec1[1]);
           double p=d1/r;
          if(p<r1){
          	//Bundle bundle=new Bundle();
          	// bundle.putDouble("param", p);
          //	 bundle.putDouble("param1",q);
          	 Intent myIntent = new Intent(v.getContext(), Success.class);
          	//myIntent.putExtras(bundle);
               startActivityForResult(myIntent, 0);
           }
          else{	
          	 Intent myIntent = new Intent(v.getContext(),Fail.class);
               startActivityForResult(myIntent, 0);
           }
			}
			catch (Exception e) {
				// TODO: handle exception
			}
*/
}
}