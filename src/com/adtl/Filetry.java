package com.adtl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Filetry extends Activity {
    /** Called when the activity is first created. */
    EditText e1,e2;
    Button b;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.average);
        e1=(EditText)findViewById(R.id.veh);
        e2=(EditText)findViewById(R.id.avg);
        b=(Button)findViewById(R.id.b1);
        b.setOnClickListener(new OnClickListener() {

        	public void onClick(View v) {
        		try {
        			File myFile = new File("/sdcard/average.txt");
        			FileOutputStream fOut = new FileOutputStream(myFile);
        			OutputStreamWriter myOutWriter = 
        									new OutputStreamWriter(fOut);
        			myOutWriter.append(e1.getText()+","+e2.getText());
        			myOutWriter.close();
        			fOut.close();
        			Toast.makeText(getBaseContext(),
        					"Done writing SD 'mysdfile.txt'",
        					Toast.LENGTH_SHORT).show();
        			Intent myIntent = new Intent(v.getContext(),StartActivity.class);
        	        startActivityForResult(myIntent, 0);
        		} catch (Exception e) {
        			Toast.makeText(getBaseContext(), e.getMessage(),
        					Toast.LENGTH_SHORT).show();
        		}
        	}
        	}); 
    }
}
