package com.surferway.android.firemap;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DispatchActivity extends Activity {
	
	EditText editTextDispatch;
	EditText editLowerLeftLat;
	EditText editLowerLeftLong;
	EditText editUpperRightLat;
	EditText editUpperRightLong;
	Button btnSaveDispatch;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispatch);
		
		editTextDispatch = (EditText) findViewById(R.id.editTextDispatch);
		editLowerLeftLat = (EditText) findViewById(R.id.editLowerLeftLat);
		editLowerLeftLong = (EditText) findViewById(R.id.editLowerLeftLong);
		editUpperRightLat = (EditText) findViewById(R.id.editUpperRightLat);
		editUpperRightLong = (EditText) findViewById(R.id.editUpperRightLong);

		btnSaveDispatch = (Button) findViewById(R.id.btnSaveDispatch);
		loadSavedPreferences();
		
		btnSaveDispatch.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	savePreferences("storedDispatch", editTextDispatch.getText().toString());
            	savePreferences("lowerLeftLat", editLowerLeftLat.getText().toString());
            	savePreferences("lowerLeftLong", editLowerLeftLong.getText().toString());
            	savePreferences("upperRightLat", editUpperRightLat.getText().toString());
            	savePreferences("upperRightLong", editUpperRightLong.getText().toString());
            	finish();
            	Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainIntent);
            }
        });
	}
	
	private void loadSavedPreferences() {
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    String dispatch = sharedPreferences.getString("storedDispatch", "9999");
	    editTextDispatch.setText(dispatch);
	    String lowerLeftLat = sharedPreferences.getString("lowerLeftLat", "50.119037");
	    editLowerLeftLat.setText(lowerLeftLat);
	    String lowerLeftLong = sharedPreferences.getString("lowerLeftLong", "-116.350071");
	    editLowerLeftLong.setText(lowerLeftLong);
	    String upperRightLat = sharedPreferences.getString("upperRightLat", "50.784451");
	    editUpperRightLat.setText(upperRightLat);
	    String upperRightLong = sharedPreferences.getString("upperRightLong", "-115.491765");
	    editUpperRightLong.setText(upperRightLong);
	}

	private void savePreferences(String key, String value) {
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    Editor editor = sharedPreferences.edit();
	    editor.putString(key, value);
	    editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dispatch, menu);
		return true;
	}

}

