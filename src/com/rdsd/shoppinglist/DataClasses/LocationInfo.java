package com.rdsd.shoppinglist.DataClasses;

import com.google.android.gms.location.Geofence;

public class LocationInfo {
	/*
	 * Use to set an expiration time for a geofence. After this amount of time
	 * Location Services will stop tracking the geofence.
	 */
	private static final long SECONDS_PER_HOUR = 60;
	private static final long MILLISECONDS_PER_SECOND = 1000;
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	private static final long GEOFENCE_EXPIRATION_TIME = GEOFENCE_EXPIRATION_IN_HOURS
			* SECONDS_PER_HOUR * MILLISECONDS_PER_SECOND;
	
	private int productId;
	private String latitude;
	private String longitude;

	public int getProductId() {
		return productId;
	}

	public LocationInfo(int productId, String latitude, String longitude) {
		this.productId = productId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setProductName(int productName) {
		this.productId = productName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Creates a Location Services Geofence object from a SimpleGeofence.
	 * 
	 * @return A Geofence object
	 */
	public Geofence toGeofence() {
		// Build a new Geofence object
		return new Geofence.Builder().setRequestId(Integer.toString(getProductId()))
				.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
				.setCircularRegion(Double.parseDouble(getLatitude()), Double.parseDouble(getLongitude()), 500)
				.setExpirationDuration(GEOFENCE_EXPIRATION_TIME).build();
	}
}
