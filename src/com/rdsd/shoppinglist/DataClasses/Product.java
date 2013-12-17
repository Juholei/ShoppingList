package com.rdsd.shoppinglist.DataClasses;

import java.util.ArrayList;

import com.rdsd.shoppinglist.Interfaces.Observer;
import com.rdsd.shoppinglist.Interfaces.Subject;


public class Product implements Subject {
	private ArrayList<Observer> observers;
	private int id;
	private String name;
	private String description;
	
	public Product() {
		this.observers = new ArrayList<Observer>();
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void notifyObservers() {
		for(Observer o : observers) {
			o.update(this);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
