package com.rdsd.shoppinglist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;

public class BoughtProductsActivity extends Activity {

	protected static final String TAG = "BoughtProductsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bought_products);
		SQLiteHelper db = new SQLiteHelper(this);

		ListView boughtProductsList = (ListView) findViewById(R.id.boughtProductsList);
		final ArrayList<String> productsList = db.getBoughtProducts();

		boughtProductsList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, productsList));

		boughtProductsList
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						SQLiteHelper db = new SQLiteHelper(
								getApplicationContext());
						ShoppingList list = db.getShoppingListFromDatabase();

						ProductObserver po = new ProductObserver(db);
						String productName = productsList.get(position);
						Product product = db.getProductByName(productName);

						if (product != null) {
							product.addObserver(po);
						} else {
							product = new Product();
							product.addObserver(po);
							product.setName(productName);
						}
						list.addToList(product);

						db.saveShoppingListToDatabase(list);
						
						Toast toast = Toast.makeText(getApplicationContext(), productName + " added to shopping list", Toast.LENGTH_SHORT);
						toast.show();
						return false;
					}
				});
	}
}
