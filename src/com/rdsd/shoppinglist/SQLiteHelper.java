package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;
import com.rdsd.shoppinglist.Interfaces.DatabaseInterface;

public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseInterface {

	private static final String TAG = "SQLiteHelper";

	private static final String DATABASE_NAME = "shoppinglist_db.db";
	private static final int DATABASE_VERSION = 3;

	private static final String TABLE_PRODUCT = "product";
	private static final String PRODUCT_ID = "product_id";
	private static final String PRODUCT_NAME = "product_name";
	private static final String PRODUCT_DESC = "product_description";
	private static final String PRODUCT_SIZE = "product_size";
	// Product classes added later
	// private String[] testcolumns = { PRODUCT_ID, PRODUCT_NAME };

	private static final String TABLE_SHOPPINGLIST = "shoppinglist";
	private static final String SHOPPINGLIST_PRODUCT = "product";
	private static final String SHOPPINGLIST_AMOUNT = "amount";
	private static final String SHOPPINGLIST_STATE = "state";

	private static final String TABLE_BOUGHTPRODUCTS = "boughtproducts";
	private static final String BOUGHTPRODUCTS_ID = "purchase_id";
	private static final String BOUGHTPRODUCTS_PRODUCT = "product";
	private static final String BOUGHTPRODUCTS_LOC_LAT = "latitude";
	private static final String BOUGHTPRODUCTS_LOC_LON = "longitude";
	private static final String BOUGHTPRODUCTS_TIMESTAMP = "timestamp";

	private SQLiteDatabase database;

	private static final String PRODUCT_CREATE = "create table "
			+ TABLE_PRODUCT + "(" + PRODUCT_ID
			+ " integer primary key autoincrement, " + PRODUCT_NAME + " text, "
			+ PRODUCT_DESC + " text, " + PRODUCT_SIZE + " integer" + ");";

	private static final String SHOPPINGLIST_CREATE = "create table "
			+ TABLE_SHOPPINGLIST + "(" + SHOPPINGLIST_PRODUCT
			+ " integer primary key" + ", " + SHOPPINGLIST_AMOUNT
			+ " integer, " + SHOPPINGLIST_STATE + " integer" + ", foreign key("
			+ SHOPPINGLIST_PRODUCT + ") references " + TABLE_PRODUCT + "("
			+ PRODUCT_ID + ")" + ");";

	private static final String BOUGHTPRODUCTS_CREATE = "create table "
			+ TABLE_BOUGHTPRODUCTS + "(" + BOUGHTPRODUCTS_ID
			+ " integer primary key autoincrement, " + BOUGHTPRODUCTS_PRODUCT
			+ " integer not null" + ", " + BOUGHTPRODUCTS_LOC_LAT + " text, "
			+ BOUGHTPRODUCTS_LOC_LON + " text, " + BOUGHTPRODUCTS_TIMESTAMP
			+ " text, " + "foreign key(" + BOUGHTPRODUCTS_PRODUCT
			+ ") references " + TABLE_PRODUCT + "(" + PRODUCT_ID + ")" + ");";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(PRODUCT_CREATE);
		database.execSQL(SHOPPINGLIST_CREATE);
		database.execSQL(BOUGHTPRODUCTS_CREATE);
		Log.v(TAG, "databases created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOUGHTPRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPINGLIST);
		onCreate(db);
	}

	// public void open() throws SQLException {
	// database = sqlitehelper.getWritableDatabase();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rdsd.shoppinglist.Interfaces.DatabaseInterface#saveProductToDatabase
	 * (com.rdsd.shoppinglist.DataClasses.Product)
	 */
	@Override
	public long saveProductToDatabase(Product p) {
		database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, p.getName());
		Log.v(TAG, values.getAsString(PRODUCT_NAME));
		long insertId = database.insertWithOnConflict(TABLE_PRODUCT, null,
				values, SQLiteDatabase.CONFLICT_REPLACE);

		if (insertId == -1) {
			Log.v(TAG,
					"Saving Product to database failed. Product: "
							+ p.getName());
		} else {
			Log.v(TAG, "Saved Product to database. Insert ID: " + insertId
					+ " Product: " + p.getName());
		}
		// Testi
		// List<Product> products = new ArrayList<Product>();
		//
		// Cursor cursor = database.query(TABLE_PRODUCT, testcolumns, null,
		// null, null, null, null);
		// cursor.moveToFirst();
		// while (!cursor.isAfterLast()) {
		// Product product = cursorToProduct(cursor);
		// products.add(product);
		// Log.v(TAG, product.getName());
		// Log.v(TAG, Integer.toString(product.getId()));
		// cursor.moveToNext();
		// }

		database.close();

		return insertId;
	}

	// Need to add the other information later
	private Product cursorToProduct(Cursor cursor) {
		Product product = new Product();
		product.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
		product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));

		return product;
	}

	@Override
	public ShoppingList getShoppingListFromDatabase() {
		database = this.getReadableDatabase();
		ShoppingList list = new ShoppingList();
		Cursor cursor = database.query(SQLiteHelper.TABLE_SHOPPINGLIST, null,
				null, null, null, null, SQLiteHelper.SHOPPINGLIST_PRODUCT
						+ " DESC");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex(SHOPPINGLIST_PRODUCT));
			Product p = getProductById(id);

			if (p != null) {
				list.addToList(p);
			}
			cursor.moveToNext();
		}
		cursor.close();
		database.close();

		return list;
	}

	@Override
	public Product getProductByName(String productName) {
		database = this.getReadableDatabase();

		Cursor cursor = database.query(SQLiteHelper.TABLE_PRODUCT, null,
				PRODUCT_NAME + " like " + productName, null, null, null, null);
		Product p;
		if (cursor.moveToFirst()) {
			p = cursorToProduct(cursor);
		} else {
			p = null;
		}
		cursor.close();
		database.close();

		return p;
	}

	private Product getProductById(int id) {
		database = this.getReadableDatabase();

		Cursor cursor = database.query(SQLiteHelper.TABLE_PRODUCT, null,
				PRODUCT_ID + " like " + id, null, null, null, null);
		Product p;
		if (cursor.moveToFirst()) {
			p = cursorToProduct(cursor);
		} else {
			p = null;
		}
		database.close();

		return p;
	}

	@Override
	public void saveShoppingListToDatabase(ShoppingList list) {

		database = this.getWritableDatabase();

		ArrayList<Product> datalist = new ArrayList<Product>(list.getContents());
		Iterator<Product> iter = datalist.iterator();
		while (iter.hasNext()) {
			Product prod = iter.next();
			createRow(prod);
		}
		database.close();
	}

	public void createRow(Product p) {
		ContentValues values = new ContentValues();
		values.put(SHOPPINGLIST_PRODUCT, p.getId());
		// Log.v(TAG, values.getAsString(SHOPPINGLIST_PRODUCT));
		long insertId = database.insertWithOnConflict(TABLE_SHOPPINGLIST, null,
				values, SQLiteDatabase.CONFLICT_REPLACE);

		if (insertId == -1) {
			Log.v(TAG,
					"Saving Product to shopping list database failed. Product: "
							+ p.getName());
		} else {
			Log.v(TAG, "Saved Product to shopping list database. Insert ID: "
					+ insertId + " Product: " + p.getName());
		}
	}

	public List<String> getProductNames() {
		database = this.getReadableDatabase();
		ArrayList<String> names = new ArrayList<String>();
		String[] columns = { SQLiteHelper.PRODUCT_NAME };
		Cursor cursor = database.query(SQLiteHelper.TABLE_PRODUCT, columns,
				null, null, null, null, SQLiteHelper.PRODUCT_NAME + " ASC");

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			String name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
			names.add(name);
			cursor.moveToNext();
		}

		cursor.close();
		database.close();

		return names;
	}

	public void moveFromShoppingListToBoughtProducts(Product p, Location loc) {
		database = getWritableDatabase();

		database.delete(TABLE_SHOPPINGLIST,
				SHOPPINGLIST_PRODUCT + " = " + p.getId(), null);

		ContentValues values = new ContentValues();
		values.put(BOUGHTPRODUCTS_PRODUCT, p.getId());
		values.put(BOUGHTPRODUCTS_LOC_LAT, Double.toString(loc.getLatitude()));
		values.put(BOUGHTPRODUCTS_LOC_LON, Double.toString(loc.getLongitude()));

		Long time = System.currentTimeMillis() / 1000;
		String timestamp = time.toString();

		values.put(BOUGHTPRODUCTS_TIMESTAMP, timestamp);

		long insertID = database.insert(TABLE_BOUGHTPRODUCTS, null, values);

		if (insertID != -1) {
			Log.v(TAG, "Bought product succesfully saved to "
					+ TABLE_BOUGHTPRODUCTS);
		}
		else {
			Log.v(TAG, "Saving bought product to "
					+ TABLE_BOUGHTPRODUCTS + " failed");
		}

		database.close();
	}
}
