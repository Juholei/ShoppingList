package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import com.rdsd.shoppinglist.DataClasses.Product;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<Product> {

	private Context context;
	private int layoutResourceId;
	private List<Product> shoppingList = null;

	public ListItemAdapter(Context context, int resource,
			List<Product> shoppingList) {
		super(context, resource, shoppingList);
		this.layoutResourceId = resource;
		this.context = context;
		this.shoppingList = shoppingList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);

		View listItem = convertView;
		ListItemHolder holder = null;

		if (listItem == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listItem = inflater.inflate(layoutResourceId, parent, false);

			holder = new ListItemHolder();
			holder.title = (TextView) listItem.findViewById(R.id.title);

			listItem.setTag(holder);

		} else {
			holder = (ListItemHolder) listItem.getTag();
		}
		
		holder.title.setText(shoppingList.get(position).getName());
		return listItem;
	}

	static class ListItemHolder {
		TextView title;
	}
}
