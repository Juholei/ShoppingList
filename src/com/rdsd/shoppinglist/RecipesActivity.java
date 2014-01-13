package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.support.v4.app.NavUtils;

public class RecipesActivity extends Activity {
	
	private final static String TAG = "RecipesActivity";
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes);
		
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.expRecipes);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Liha Pulla");
        listDataHeader.add("Kana Risotto");
        listDataHeader.add("Jallu Kola");
 
        // Adding child data
        List<String> lihaPulla = new ArrayList<String>();
        lihaPulla.add("Jauhe");
        lihaPulla.add("Liha");
        lihaPulla.add("Viina");
 
        List<String> kanaRisotto = new ArrayList<String>();
        kanaRisotto.add("Kana");
        kanaRisotto.add("Riisi");
        kanaRisotto.add("Porkkana");
        kanaRisotto.add("Herne");
        kanaRisotto.add("Maissi");
        kanaRisotto.add("Viina");
 
        List<String> jalluKola = new ArrayList<String>();
        jalluKola.add("Jallu");
        jalluKola.add("Kola");
 
        listDataChild.put(listDataHeader.get(0), lihaPulla); // Header, Child data
        listDataChild.put(listDataHeader.get(1), kanaRisotto);
        listDataChild.put(listDataHeader.get(2), jalluKola);
		
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
