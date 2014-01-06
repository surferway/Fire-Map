package com.surferway.android.firemap;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
	 
    // Google Map
    private GoogleMap googleMap;
    private String strAddress;
    private String emerg;
    private String emergType;    
    List<Address> address;
    private LatLng call;
    
	private String lowerLeftLat;
	private String lowerLeftLong;
	private String upperRightLat;
	private String upperRightLong;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    lowerLeftLat = sharedPreferences.getString("lowerLeftLat", "50.119037");
	    lowerLeftLong = sharedPreferences.getString("lowerLeftLong", "-116.350071");
	    upperRightLat = sharedPreferences.getString("upperRightLat", "50.784451");
	    upperRightLong = sharedPreferences.getString("upperRightLong", "-115.491765");
 
        try {
        	
        	Intent i = getIntent();
            // Receiving the Data
            strAddress = i.getStringExtra("address");
            emerg = i.getStringExtra("calltype");
            emergType = emerg.replace("Address", "");
            // Load map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
    
 
    /**
     * Uses SupportMapFragment for use in Gingerbread
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            //googleMap = ((MapFragment) getFragmentManager().findFragmentById(
            //        R.id.map)).getMap();
        	FragmentManager fmanager = getSupportFragmentManager();
            Fragment fragment = fmanager.findFragmentById(R.id.map);
            SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
            googleMap = supportmapfragment.getMap(); 
            
    		Double lat = null;
    		Double lon = null;
    		Double lowerLeftLatitude = Double.parseDouble(lowerLeftLat);
    		Double lowerLeftLongitude = Double.parseDouble(lowerLeftLong);
    		Double upperRightLatitude = Double.parseDouble(upperRightLat);
    		Double upperRightLongitude = Double.parseDouble(upperRightLong);
            
            Geocoder coder = new Geocoder(this);
            
            try {
				address = coder.getFromLocationName(strAddress,5,lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRightLongitude);
				if (address.size() > 0) {
		            lat = (double) (address.get(0).getLatitude());
		            lon = (double) (address.get(0).getLongitude());

		            call = new LatLng(lat, lon);
		            /*used marker for show the location */
	
		            googleMap.addMarker(new MarkerOptions()
                    .position(call)
                    .title(strAddress)
                    .snippet(emergType));	
		            	            
		            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		            // Move the camera instantly to with a zoom of 15.
		            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(call, 15));

		            // Zoom in, animating the camera.
		            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);	            
		            
		            // check if map is created successfully or not
		            if (googleMap == null) {
		                Toast.makeText(getApplicationContext(),
		                        "Unable to create map", Toast.LENGTH_SHORT)
		                        .show();
		            }
		            
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

 
}

