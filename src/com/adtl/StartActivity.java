package com.adtl;
import java.io.*;
import java.nio.MappedByteBuffer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class StartActivity extends Activity {

	Button e1,e2,e3;
	
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	e1= (Button) findViewById(R.id.b1);
	e2= (Button) findViewById(R.id.b2);
	e3= (Button) findViewById(R.id.b3);

	e1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), Filetry.class);
	        startActivityForResult(myIntent, 0);
		}
		}); 
	e2.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), MappingDemoActivity.class);
	        startActivityForResult(myIntent, 0);
		}
		}); 
	e3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(v.getContext(), addpetrol.class);
	        startActivityForResult(myIntent, 0);
		}
		}); 
		
}

}



