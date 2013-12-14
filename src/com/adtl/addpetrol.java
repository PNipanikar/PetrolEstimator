package com.adtl;




import java.io.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class addpetrol extends Activity {

	EditText e1,e2,e3;
	Button b;
public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addpet);
	e1= (EditText) findViewById(R.id.veh);
	e2= (EditText) findViewById(R.id.av);
	//e3= (EditText) findViewById(R.id.pq);
	b = (Button) findViewById(R.id.b1);
	b.setOnClickListener(new OnClickListener() {
	
		public void onClick(View v) {
		
			try {
			File myFile = new File("/sdcard/petrolq.txt");
			myFile.createNewFile();
			if(myFile.length()==0){
                FileOutputStream fOut = new FileOutputStream(myFile);
			    OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
			    myOutWriter.append(","+0);
			                myOutWriter.close();
			    fOut.close();
            }

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
            double z=Double.parseDouble(e2.getText().toString());
             double c=y+z;
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(e1.getText()+","+c);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'petrol.txt'",
					Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(v.getContext(),StartActivity.class);
	        startActivityForResult(myIntent, 0);
		} 
			catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
			}
	}
	});
}
}



