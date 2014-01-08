package com.rdsd.shoppinglist;

import android.util.Log;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;

/**
 * Saves created or changed Product objects to database
 *
 */
public class ProductObserver implements Observer {

	private SQLiteHelper dbHelper;
	private final static String TAG = "ProductObserver";
	
	public ProductObserver(SQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public void update(Subject o) {
		Log.v(TAG, "Update called");
		Product p = (Product) o;
		dbHelper.saveProductToDatabase(p);
	}

}
