package com.rdsd.shoppinglist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BoughtProductsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_bought_products);
		SQLiteHelper db = new SQLiteHelper(this);
		
		ListView boughtProductsList = (ListView) findViewById(R.id.boughtProductsList);
		ArrayList<String> list = db.getBoughtProducts();
		
		boughtProductsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		db.getLocationInfoForShoppingList();


	}

}
