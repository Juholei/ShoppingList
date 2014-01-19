package com.rdsd.shoppinglist;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;

public class CreateShoppingListActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private final static String TAG = "CreateShoppingListActivity";
	private ShoppingList list;
	private LocationClient lc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		lc = new LocationClient(this, this, this);

		setContentView(R.layout.activity_create_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();

		// Instantiate sqlitehelper
		final SQLiteHelper db = new SQLiteHelper(getApplicationContext());
		list = db.getShoppingListFromDatabase();

		ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_dropdown_item_1line,
				db.getProductNames());
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.itemField);
		textView.setAdapter(autoCompleteAdapter);

		ListView productList = (ListView) findViewById(R.id.productList);
		final ListItemAdapter adapter = new ListItemAdapter(
				getApplicationContext(), R.layout.shoppinglist_item, list, lc,
				db);
		productList.setAdapter(adapter);

		final ProductObserver po = new ProductObserver(db);
		final ShoppingListObserver slo = new ShoppingListObserver(db);
		list.addObserver(slo);
		list.addObserversToExistingProducts(po);

		Button addButton = (Button) findViewById(R.id.addItemButton);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AutoCompleteTextView itemTextView = (AutoCompleteTextView) findViewById(R.id.itemField);
				String productName = itemTextView.getText().toString();
				if (!productName.equals(null) && !productName.equals("")) {
					Product product = db.getProductByName(productName);

					if (product != null) {
						product.addObserver(po);
					} else {
						product = new Product();
						product.addObserver(po);
						product.setName(productName);
					}
					list.addToList(product);

					itemTextView.setText("");
					adapter.notifyDataSetChanged();

				}
			}
		});

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onStart() {
		super.onStart();
		lc.connect();

	}

	@Override
	protected void onStop() {
		lc.disconnect();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_shopping_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Google-provided code from
	 * http://developer.android.com/training/location/retrieve-current.html
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this, 9000);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			Log.v(TAG, connectionResult.getErrorCode() + "");
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.v(TAG, "LocationClient connected");

	}

	@Override
	public void onDisconnected() {
		Log.v(TAG, "LocationClient disconnected");
	}

}
