package com.adtl;

import java.io.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Success extends Activity {
	Button e1,e2;
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.yes);
	//e1= (Button) findViewById(R.id.b1);
	e1=(Button)findViewById(R.id.yes);
	e2=(Button)findViewById(R.id.no);
//Double m=tryit();
	//Bundle bundle=getIntent().getExtras();
	//Double name=bundle.getDouble("param");
	//Double s=getIntent().getExtras().getDouble("param1");
	//t.setText(""+name);
	//e3= (EditText) findViewById(R.id.pq);
e1.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		Bundle bundle=getIntent().getExtras();
		Double name=bundle.getDouble("param2");
	
		//t.setText(""+name1);
		
		//Double s=getIntent().getExtras().getDouble("param1");
		try {
			File myFile = new File("/sdcard/petrolq.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			aDataRow = myReader.readLine();
			 String rec[]=aDataRow.split("[,]");
             String val1 = rec[0];
             String val2 = rec[1];
             
            myFile = new File("/sdcard/petrolq.txt");
//            / myFile.createNewFile();
             double y=Double.parseDouble(rec[1]);
            //double z=Double.parseDouble(e2.getText().toString());
             double c=y-name;
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(val1+","+c);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'petrol.txt'",
					Toast.LENGTH_SHORT).show();
	//		t.setText(""+name);
			Intent myIntent = new Intent(v.getContext(),StartActivity.class);
	        startActivityForResult(myIntent, 0);
			
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		
	}// onClick
	}); // btnWriteSDFile
e2.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		
		Intent myIntent = new Intent(v.getContext(),StartActivity.class);
        startActivityForResult(myIntent, 0);
	}// onClick
	}); // btnWriteSDFile
	
		
}
/*
		btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
		btnReadSDFile.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// write on SD card file data in the text box
		try {
			File myFile = new File("/sdcard/mysdfile.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			txtData.setText(aBuffer);
			myReader.close();
			Toast.makeText(getBaseContext(),
					"Done reading SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		}// onClick
		}); // btnReadSDFile

		btnClearScreen = (Button) findViewById(R.id.btnClearScreen);
		btnClearScreen.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				txtData.setText("");
			}
		}); // btnClearScreen

		btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// clear text box
				finish();
			}
		}); // btnClose

	}// onCreate
*/

}



