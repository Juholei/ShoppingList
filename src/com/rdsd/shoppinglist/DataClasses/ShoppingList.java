package com.rdsd.shoppinglist.DataClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;

public class ShoppingList {
	private static final String TAG = "ShoppingList";
	private HashMap<Product, Integer> contents;
	private ArrayList<Product> contentsForListView;

	public ShoppingList() {
		this.contents = new HashMap<Product, Integer>();
		contentsForListView = new ArrayList<Product>();
	}

	public void addToList(Product product, int amount) {
		contents.put(product, amount);
		updateContentsForListView();
	}

	/**
	 * @return an ArrayList to be used with ArrayAdapter
	 */
	public ArrayList<Product> getContentsForListView() {
	
		return contentsForListView;
	}
	
	private void updateContentsForListView() {
		contentsForListView.clear();
		for(Product p : contents.keySet()) {
			contentsForListView.add(0, p);
		}
	}
}
