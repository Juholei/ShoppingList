package com.rdsd.shoppinglist;

import com.rdsd.shoppinglist.DataClasses.ShoppingList;
import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;

public class ShoppingListObserver implements Observer {
	
/**
 * Saves created or changed Product objects to database
 *
 */

	private SQLiteHelper dbHelper;
	
	public ShoppingListObserver(SQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public void update(Subject o) {
		ShoppingList p = (ShoppingList) o;
		dbHelper.saveShoppingListToDatabase(p);
	}

}
