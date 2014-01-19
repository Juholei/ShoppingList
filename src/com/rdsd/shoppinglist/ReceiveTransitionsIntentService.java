package com.rdsd.shoppinglist;

import java.util.List;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.rdsd.shoppinglist.DataClasses.Product;

public class ReceiveTransitionsIntentService extends IntentService {
	private static final String TAG = "ReceiveTransitionsIntentService";

	public ReceiveTransitionsIntentService() {
		super("ReceiveTransitionsIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (LocationClient.hasError(intent)) {
			int errorCode = LocationClient.getErrorCode(intent);

			Log.e(TAG, "Location services had an error with error code "
					+ errorCode);
		} else {
			int transitionType = LocationClient.getGeofenceTransition(intent);

			if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
				List<Geofence> triggered = LocationClient
						.getTriggeringGeofences(intent);

				String[] triggerIds = new String[triggered.size()];
				SQLiteHelper db = new SQLiteHelper(this);
				for (int i = 0; i < triggerIds.length; i++) {
					triggerIds[i] = triggered.get(i).getRequestId();
					Log.v(TAG, "Geofence triggered! ID " + triggerIds[i]);
					Product p = db.getProductById(Integer.parseInt(triggerIds[i]));
					Notification n = new Notification.Builder(this)
							.setContentTitle(
									"Go buy " + p.getName() + " , you can buy it nearby!")
							.setContentText(
									"Go buy this product: " + p.getName())
							.setTicker("Go buy " + p.getName() + " , you can buy it nearby!")
							.setSmallIcon(R.drawable.ic_launcher)
							.setWhen(System.currentTimeMillis())
							.setDefaults(
									Notification.DEFAULT_LIGHTS
											| Notification.DEFAULT_VIBRATE)
							.build();
					n.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
					

					NotificationManager nm = (NotificationManager) this
							.getSystemService(Context.NOTIFICATION_SERVICE);

					nm.notify(Integer.parseInt(triggerIds[i]), n);
				}
			} else {
				Log.e(TAG,
						"Error with Geofence transitions "
								+ Integer.toString(transitionType));
			}
		}
	}
}
