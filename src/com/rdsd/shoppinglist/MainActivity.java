package com.rdsd.shoppinglist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;
import com.rdsd.shoppinglist.DataClasses.LocationInfo;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, OnAddGeofencesResultListener {

	private ArrayList<Geofence> geofences;

	private LocationClient locationClient;
	// The intent used to request monitoring of geofences
	private PendingIntent GeofenceRequestIntent;
	
	//SET TO TRUE TO TEST WITH MOCK LOCATIONS
	private boolean locationMockmode = false;

	public enum REQUEST_TYPE {
		ADD
	};

	private REQUEST_TYPE requestType;
	private boolean requestInProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startmenu);
		geofences = new ArrayList<Geofence>();
		requestInProgress = false;

		createGeofenceObjects();
		addGeofences();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void goToShoppingList(View view) {
		Intent intent = new Intent(this, CreateShoppingListActivity.class);
		startActivity(intent);
	}

	public void goToBoughtProductsList(View view) {
		Intent intent = new Intent(this, BoughtProductsActivity.class);
		startActivity(intent);
	}

	public void goToRecipes(View view) {
		Intent intent = new Intent(this, RecipesActivity.class);
		startActivity(intent);
	}

	public void createGeofenceObjects() {
		SQLiteHelper db = new SQLiteHelper(this);

		ArrayList<LocationInfo> productLocations = db
				.getLocationInfoForShoppingList();

		for (LocationInfo loc : productLocations) {
			geofences.add(loc.toGeofence());
		}
	}

	public void addGeofences() {
		requestType = REQUEST_TYPE.ADD;
		locationClient = new LocationClient(this, this, this);

		if (!requestInProgress) {
			requestInProgress = true;
			locationClient.connect();
		} else {
			locationClient.disconnect();
			requestInProgress = false;
			addGeofences();
		}
	}

	private PendingIntent getTransitionPendingIntent() {
		Intent intent = new Intent(this, ReceiveTransitionsIntentService.class);

		return PendingIntent.getService(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		if (statusCode == LocationStatusCodes.SUCCESS) {
			Log.v(MainActivity.class.getName(), "Geofences added succesfully");
			if (locationMockmode) {
				SQLiteHelper db = new SQLiteHelper(this);
				ArrayList<LocationInfo> locations = db
						.getLocationInfoForShoppingList();
				Location mockLocation = new Location("flp");
				mockLocation.setLatitude(Double.parseDouble(locations.get(0)
						.getLatitude()));
				mockLocation.setLongitude(Double.parseDouble(locations.get(0)
						.getLongitude()));
				mockLocation.setAccuracy(3.0f);
				locationClient.setMockLocation(mockLocation);
			}
		} else {
			Log.v(MainActivity.class.getName(),
					"Adding Geofences failed horribly :(((");
		}

		requestInProgress = false;
		locationClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		requestInProgress = false;
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this, 9000);
			} catch (SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
			// If no resolution is available, display an error dialog
		} else {
			// Get the error code
			int errorCode = connectionResult.getErrorCode();
			Log.v(MainActivity.class.getName(),
					"Connecting to Google Play Location Services failed with error code "
							+ errorCode);
		}
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		if (locationMockmode) {
			// Mocking the location for testing
			locationClient.setMockMode(true);
			Location mockLocation = new Location("flp");
			mockLocation.setLatitude(0);
			mockLocation.setLongitude(0);
			mockLocation.setAccuracy(3.0f);
			locationClient.setMockLocation(mockLocation);
		}
		switch (requestType) {
		case ADD:
			if (!geofences.isEmpty()) {
				GeofenceRequestIntent = getTransitionPendingIntent();
				locationClient.addGeofences(geofences, GeofenceRequestIntent,
						this);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		createGeofenceObjects();
		addGeofences();
	}

	@Override
	public void onDisconnected() {
		requestInProgress = false;
		locationClient = null;
	}

}
