package com.madeja.gpslib;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPSLib {
	
	protected Context context;
	protected boolean oznamujLokaciu;
	
	public GPSLib(Context context) {
		this.context = context;
	}
	
	public void zapniGPS() {
		LocationManager mlocManager = 
				(LocationManager)context.getSystemService(Context.LOCATION_SERVICE); 
		LocationListener mlocListener = new MyLocationListener(); 
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		oznamujLokaciu = true;
	}
	
	public void vypniGPS() {
		oznamujLokaciu = false;
	}


public class MyLocationListener implements LocationListener { 

	@Override 
	public void onLocationChanged(Location loc) {
		if (!oznamujLokaciu) {
			return;
		}
		loc.getLatitude(); 
		loc.getLongitude(); 
		String Text = "Súèasná pozícia:\n" + 
				"Svetová šírka = " + loc.getLatitude() + 
				"\nSvetová dåžka = " + loc.getLongitude(); 

		Toast.makeText( context, 
				Text, 
				Toast.LENGTH_SHORT).show(); 
	} 


	@Override 
	public void onProviderDisabled(String provider) 
	{ 
		Toast.makeText( context, 
				"Gps vypnuté", 
				Toast.LENGTH_SHORT ).show(); 
	} 


	@Override 
	public void onProviderEnabled(String provider) 
	{ 
		Toast.makeText( context, 
				"Gps zapnuté", 
				Toast.LENGTH_SHORT).show(); 
	} 


	@Override 
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{ 

	} 

}
}
