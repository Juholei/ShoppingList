package com.rdsd.shoppinglist;

import android.util.Log;

import com.rdsd.shoppinglist.DataClasses.ShoppingList;
import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;

public class ShoppingListObserver implements Observer {
	
/**
 * Saves created or changed Product objects to database
 *
 */

	private SQLiteHelper dbHelper;
	private final static String TAG = "ShoppingListObserver";
	
	public ShoppingListObserver(SQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public void update(Subject o) {
		Log.v(TAG, "Update called");
		ShoppingList p = (ShoppingList) o;
		dbHelper.saveShoppingListToDatabase(p);
	}

}
