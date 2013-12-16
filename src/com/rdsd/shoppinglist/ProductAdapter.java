package com.rdsd.shoppinglist;

import java.util.List;

import com.rdsd.shoppinglist.DataClasses.Product;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ProductAdapter extends ArrayAdapter<Product>{

	public ProductAdapter(Context context, int resource,
			int textViewResourceId, List<Product> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
