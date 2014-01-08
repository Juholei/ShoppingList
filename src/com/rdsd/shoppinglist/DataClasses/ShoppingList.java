package com.rdsd.shoppinglist.DataClasses;

import java.util.ArrayList;

import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;

public class ShoppingList implements Subject {
	
	private static final String TAG = "ShoppingList";
	private ArrayList<Product> contents;
	private ArrayList<Observer> observers;

	public ShoppingList() {
		this.observers = new ArrayList<Observer>();
		contents = new ArrayList<Product>();
	}
	
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	public void notifyObservers() {
		for(Observer o : observers) {
			o.update(this);
		}
	}
	
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void addToList(Product product) {
		contents.add(0, product);
		notifyObservers();
	}

	public boolean removeProduct(Product product) {
		return contents.remove(product);
	}
	/**
	 * @return an ArrayList to be used with ArrayAdapter
	 */
	public ArrayList<Product> getContents() {
		return contents;
	}
	
	
}
