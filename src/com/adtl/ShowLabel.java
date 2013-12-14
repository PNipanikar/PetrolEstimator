package com.adtl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLabel extends Activity {
    /** Called when the activity is first created. */
	int s;
	double p;
	TextView t;
	Button b1,b2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlabel);
        b1=(Button)findViewById(R.id.check);
        b2=(Button)findViewById(R.id.no);
        t=(TextView)findViewById(R.id.text);
       Bundle bundle=getIntent().getExtras();
        s=bundle.getInt("param");
        final double m=(s/1000.0);
        t.setText(""+m);
        /*Intent myin = getIntent();
        s = myin.getDoubleExtra("sDistance",0);
        t.setText(""+s);*/
        b1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
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
                    p=(m/r);
                   if(p<r1){	
                    	Intent myIntent = new Intent(v.getContext(), Success.class);
                    	Bundle bundle=new Bundle();
                    	bundle.putDouble("param2", p);
                    	myIntent.putExtras(bundle);
                        startActivityForResult(myIntent, 0);
                    }
                   else{	
                    	 Intent myIntent = new Intent(v.getContext(),Fail.class);
                         startActivityForResult(myIntent, 0);
                     }
        		}
        		catch (Exception e) {
    			}
        	}
        	});
        b2.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			Intent myIntent = new Intent(v.getContext(), options.class);
    	        startActivityForResult(myIntent, 0);
    		}
    });      
        }
}