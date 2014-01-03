package com.rdsd.shoppinglist;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;

/**
 * Saves created or changed Product objects to database
 *
 */
public class ProductObserver implements Observer {

	private SQLiteHelper dbHelper;
	
	public ProductObserver(SQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public void update(Subject o) {
		Product p = (Product) o;
		dbHelper.saveProductToDatabase(p);
	}

}
