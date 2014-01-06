package com.surferway.android.firemap;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private String dispatch;
	private String lowerLeftLat;
	private String lowerLeftLong;
	private String upperRightLat;
	private String upperRightLong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    dispatch = sharedPreferences.getString("storedDispatch", "9999");
	    lowerLeftLat = sharedPreferences.getString("lowerLeftLat", "50.119037");
	    lowerLeftLong = sharedPreferences.getString("lowerLeftLong", "-116.350071");
	    upperRightLat = sharedPreferences.getString("upperRightLat", "50.784451");
	    upperRightLong = sharedPreferences.getString("upperRightLong", "-115.491765");
	
		List<SMSData> smsList = new ArrayList<SMSData>();
		
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor c= getContentResolver().query(uri, null, null ,null,null);

		
		// Read the sms data and store it in the list
		if(c.moveToFirst()) {
			for(int i=0; i < c.getCount(); i++) {
				String smsNumber = c.getString(c.getColumnIndexOrThrow("address")).toString();
				String smsBody = c.getString(c.getColumnIndexOrThrow("body")).toString();	
				if ((smsNumber.substring(0,dispatch.length())).equals(dispatch)){
					String[] parts = smsBody.split(":");	
					SMSData sms = new SMSData();
					sms.setBody(smsBody);
					sms.setNumber(smsNumber);
					sms.setDate(c.getString(c.getColumnIndexOrThrow("date")).toString());
					
					for(int j=0; j<parts.length; j++) {
						switch(j) {
					    case 0:
					    	sms.setDispatch(parts[j]);
					        break;
					    case 1:
					    	sms.setDay(parts[j]);
					        break;
					    case 4:
					    	sms.setDepartment(parts[j]);
					        break;  
					    case 5:
					    	sms.setCallType(parts[j]);
					        break;     
					    case 6:
					    	sms.setAddress(parts[j]);
					        break;  					   
						}
						
					}
					
					//sms.setDay(parts[1]);
					//sms.setDepartment(parts[4]);
					//sms.setCallType(parts[5]);
					//sms.setAddress(parts[6]);
					
					//only texts with an address
					if (smsBody.contains("Address:")){
						smsList.add(sms);	
					}
									
				}
				c.moveToNext();
			}
		}
		c.close();
		
		if (smsList.isEmpty()){
			SMSData sms = new SMSData();
			sms.setDay("Please enter Dispatch number from which you receive your texts.");
			sms.setCallType("");
			sms.setAddress("Click here to enter your Dispatch number.");
			smsList.add(sms);        
		} 
		
		// Set smsList in the ListAdapter
		setListAdapter(new ListAdapter(this, smsList));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	 
		switch (item.getItemId()) {
	 
			// set dispatch number activity
			case R.id.action_settings:
				Intent dispatchIntent = new Intent(getApplicationContext(), DispatchActivity.class);
				startActivity(dispatchIntent);
				return true;
	  
			default:
	 
			return super.onOptionsItemSelected(item);
		} 
	}
	 
	public void showToast(String message){
		Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT); 
		toast.show(); 
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		v.setBackgroundColor(Color.YELLOW);
		SMSData sms = (SMSData)getListAdapter().getItem(position);
		List<Address> address;
		Geocoder coder = new Geocoder(this);
		String strAddress = sms.getAddress();
		String strCallType = sms.getCallType();
		if (strCallType.length() > 0){
			strCallType.replace("Address", " ");
			
			for (int i=0; i<sms.getAddress().length(); i++){
				if ((sms.getAddress().charAt(i) == ',') || (sms.getAddress().charAt(i) == '<')){	
					strAddress = sms.getAddress().substring(0, i);
					break;
				}			
			}
			
			try{
					int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
					if(status == ConnectionResult.SUCCESS) {
			            //This uses google maps api v2
			    		Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
			    		mapIntent.putExtra("address", strAddress);
			    		mapIntent.putExtra("calltype", strCallType);
			    		startActivity(mapIntent);
					} else {
			            //This uses google maps in browser
			    		Double lowerLeftLatitude = Double.parseDouble(lowerLeftLat);
			    		Double lowerLeftLongitude = Double.parseDouble(lowerLeftLong);
			    		Double upperRightLatitude = Double.parseDouble(upperRightLat);
			    		Double upperRightLongitude = Double.parseDouble(upperRightLong);
			    		
						address = coder.getFromLocationName(strAddress,5,lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRightLongitude);
						if (address.size() > 0) {
				            Double lat = (double) (address.get(0).getLatitude());
				            Double lon = (double) (address.get(0).getLongitude()); 
				    		String uri = "http://maps.google.com/maps?q=" + lat + ',' + lon + "(Fire)&z=15";
				    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				    		startActivity(intent);	    		
						}
					}
	
		    		showToast(strAddress);    		
				
			} catch (Exception e) {
	            e.printStackTrace();
	        }
		
		} else {
			Intent dispatchIntent = new Intent(getApplicationContext(), DispatchActivity.class);
			startActivity(dispatchIntent);	
			
		}
		

	}

}
