package com.rdsd.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class BoughtProductsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_bought_products);
		
		ListView boughtProductsList = (ListView) findViewById(R.id.boughtProductsList);
	}

}
