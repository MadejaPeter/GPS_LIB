package com.madeja.gpslib;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class GPSLib {
	
	protected Context context;
	protected boolean oznamujLokaciu;
	
	public GPSLib(Context context) {
		this.context = context;
	}
	
	public void zapniKompas(Activity aktivita, MapView mapa) {
		 MyLocationOverlay overlaySKompasom=new MyLocationOverlay(aktivita, mapa);
		 overlaySKompasom.enableCompass();
		 overlaySKompasom.enableMyLocation();
		 mapa.getOverlays().add(overlaySKompasom);
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


	public Location getCurrentLocation() {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			return location;
		}
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			return location;
		}
		return null;
	}
	private boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}
		final String packageName = context.getPackageName();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
					&& appProcess.processName.equals(packageName)) {
				return true;
			}
		}
		return false;
	}

public class MyLocationListener implements LocationListener { 

	@Override 
	public void onLocationChanged(Location loc) {
		if (!oznamujLokaciu) {
			return;
		}
		if (!isAppOnForeground(context)) {
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
	//pridanie do vzdialeneho repozitara
}
}
