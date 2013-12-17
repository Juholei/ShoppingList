package com.rdsd.shoppinglist.DataClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;

public class ShoppingList {
	private static final String TAG = "ShoppingList";
	private ArrayList<Product> contents;

	public ShoppingList() {
		contents = new ArrayList<Product>();
	}

	public void addToList(Product product) {
		contents.add(0, product);
	}

	/**
	 * @return an ArrayList to be used with ArrayAdapter
	 */
	public ArrayList<Product> getContents() {
		return contents;
	}
}
