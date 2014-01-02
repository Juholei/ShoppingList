package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;
import com.rdsd.shoppinglist.Interfaces.DatabaseInterface;

public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseInterface {

	private static final String TAG = "SQLiteHelper";
	
	private static final String DATABASE_NAME = "shoppinglist_db.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_PRODUCT = "product";
	private static final String PRODUCT_ID = "product_id";
	private static final String PRODUCT_NAME = "product_name";
	private static final String PRODUCT_DESC = "product_description";
	private static final String PRODUCT_SIZE = "product_size";
	// Product classes added later
	private String[] testcolumns = { PRODUCT_ID,
		      PRODUCT_NAME };

	private static final String TABLE_SHOPPINGLIST = "shoppinglist";
	private static final String SHOPPINGLIST_PRODUCT = "product";
	private static final String SHOPPINGLIST_AMOUNT = "amount";
	private static final String SHOPPINGLIST_STATE = "state";
	
	private SQLiteDatabase database;

	private static final String PRODUCT_CREATE = "create table "
			+ TABLE_PRODUCT + "(" + PRODUCT_ID
			+ " integer primary key autoincrement, " + PRODUCT_NAME + " text, "
			+ PRODUCT_DESC + " text, " + PRODUCT_SIZE + " integer" + ");";

	private static final String SHOPPINGLIST_CREATE = "create table "
			+ TABLE_SHOPPINGLIST + "(" + SHOPPINGLIST_PRODUCT
			+ " integer primary key" +
			", " + SHOPPINGLIST_AMOUNT
			+ " integer, " + SHOPPINGLIST_STATE + " integer" + ", foreign key(" + SHOPPINGLIST_PRODUCT + ") references "
			+ TABLE_PRODUCT + "(" + PRODUCT_ID + ")" +
			 ");";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v(TAG, PRODUCT_CREATE);
		Log.v(TAG, SHOPPINGLIST_CREATE);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(PRODUCT_CREATE);
		database.execSQL(SHOPPINGLIST_CREATE);
		Log.v(TAG, "databases created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
//	public void open() throws SQLException {
//		database = sqlitehelper.getWritableDatabase();
//	}

	@Override
	public void saveProductToDatabase(Product p) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, p.getName());
		Log.v(TAG, values.getAsString(PRODUCT_NAME));
		database.insert(TABLE_PRODUCT, null, values);
		
		//Testi
//		List<Product> products = new ArrayList<Product>();
//		
//		Cursor cursor = database.query(TABLE_PRODUCT, testcolumns, null, null, null, null, null);
//		cursor.moveToFirst();
//	    while (!cursor.isAfterLast()) {
//	      Product product = cursorToProduct(cursor);
//	      products.add(product);
//	      Log.v(TAG, product.getName());
//	      Log.v(TAG, Integer.toString(product.getId()));
//	      cursor.moveToNext();
//	    }
	    
	    
		database.close();
	}

	//TESTI
//	private Product cursorToProduct(Cursor cursor) {
//		Product product = new Product();
//	    product.setId(cursor.getInt(0));
//	    product.setName(cursor.getString(1));
//	    return product;
//}

	@Override
	public ShoppingList getShoppingListFromDatabase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductByName(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveShoppingListToDatabase(ShoppingList list) {
		// TODO Auto-generated method stub
		
	}

}
