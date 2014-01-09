package com.rdsd.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;

public class CreateShoppingListActivity extends Activity {

	private final static String TAG = "CreateShoppingListActivity";
	private ShoppingList list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();

		// Instantiate sqlitehelper
		SQLiteHelper db = new SQLiteHelper(getApplicationContext());

		list = db.getShoppingListFromDatabase();
		Button addButton = (Button) findViewById(R.id.addItemButton);
		ListView productList = (ListView) findViewById(R.id.productList);
		final ListItemAdapter adapter = new ListItemAdapter(
				getApplicationContext(), R.layout.shoppinglist_item, list);
		productList.setAdapter(adapter);

		final ProductObserver po = new ProductObserver(db);
		final ShoppingListObserver slo = new ShoppingListObserver(db);
		list.addObserver(slo);
		list.addObserversToExistingProducts(po);

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AutoCompleteTextView itemTextView = (AutoCompleteTextView) findViewById(R.id.itemField);
				String productName = itemTextView.getText().toString();
				if (!productName.equals(null) && !productName.equals("")) {
					Product product = new Product();
					product.addObserver(po);
					product.setName(productName);
					list.addToList(product);
					// Log.v(TAG, product.getName());
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

}
