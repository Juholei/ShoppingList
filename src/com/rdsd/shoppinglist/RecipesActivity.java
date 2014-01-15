package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.support.v4.app.NavUtils;

public class RecipesActivity extends Activity {

	private final static String TAG = "RecipesActivity";

	// Maybe used for showing rendering of lists
	private ProgressDialog pDialog;

	// JSON url
	private static String url_recipes = "http://91.145.121.174:1337/recipes/";
	private static String url_items = "http://91.145.121.174:1337/recipe/";

	// JSON TAGS
	private static final String TAG_RECIPES = "recipesListHeader";
	private static final String TAG_ITEM = "recipeItem";
	private static final String TAG_PRODUCTS = "products";

	// JSON Array for parsing
	JSONArray recipes = null;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	List<String> recipe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.expRecipes);
		
		new GetRecipes().execute();

		// listAdapter = new ExpandableListAdapter(this, listDataHeader,
		// listDataChild);
		//
		// // setting list adapter
		expListView.setAdapter(listAdapter);

		// get recipes in async

		// Show the Up button in the action bar.
		setupActionBar();
	}

	private class GetRecipes extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(RecipesActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			HTTPhandler hh = new HTTPhandler();

			// Making a request to url and getting response
			String jsonStr = hh.makeServiceCall(url_recipes, HTTPhandler.GET);

			// Log.d("Response: ", "> " + jsonStr);

			listDataHeader = new ArrayList<String>();
			listDataChild = new HashMap<String, List<String>>();

			if (jsonStr != null) {
				try {
					JSONArray recipenames = new JSONArray(jsonStr);

					// looping through all Recipes
					for (int i = 0; i < recipenames.length(); i++) {

						// Log.v(TAG, recipenames.getString(i));

						String jsonStr2 = hh.makeServiceCall(url_items
								+ recipenames.get(i).toString(),
								HTTPhandler.GET);
						
						List<String> recipe = new ArrayList<String>();
						
						recipe.clear();

						// Log.d(TAG, jsonStr2);

						if (jsonStr2 != null) {
							try {
								JSONObject currentrecipe = new JSONObject(
										jsonStr2);

								JSONArray products = currentrecipe
										.getJSONArray(TAG_PRODUCTS);

								for (int j = 0; j < products.length(); j++) {

									recipe.add(products.getString(j));
									// Log.v(TAG, products.getString(j));
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Log.e("HTTPHandler",
									"Couldn't get any data from the url");
						}

						// adding contact to contact list
						listDataHeader.add(recipenames.getString(i));
						listDataChild.put(listDataHeader.get(i), recipe);
//						Log.v(TAG, listDataHeader.get(i));
//						Log.v(TAG, recipe.get(i));

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("HTTPHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			// ListAdapter adapter = new SimpleAdapter(
			// MainActivity.this, listDataHeader,
			// R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
			// TAG_PHONE_MOBILE }, new int[] { R.id.name,
			// R.id.email, R.id.mobile });
			
			listAdapter = new ExpandableListAdapter(RecipesActivity.this,
					listDataHeader, listDataChild);

			// setting list adapter
			expListView.setAdapter(listAdapter);
		}

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
		getMenuInflater().inflate(R.menu.recipes, menu);
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
