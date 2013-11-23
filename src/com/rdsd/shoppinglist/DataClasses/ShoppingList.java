package com.rdsd.shoppinglist.DataClasses;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class ShoppingList {

	private HashMap<Product, Integer> contents;

	public ShoppingList() {
		this.contents = new HashMap<Product, Integer>();
	}

	public void addToList(Product product, int amount) {
		contents.put(product, amount);
	}

	/**
	 * @return Returns an iterable Set of Entries in the ShoppingList
	 */
	public Set<Entry<Product, Integer>> getContents() {

		return contents.entrySet();
	}
}
