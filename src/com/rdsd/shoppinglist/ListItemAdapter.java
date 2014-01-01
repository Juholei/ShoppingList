package com.rdsd.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import com.rdsd.shoppinglist.DataClasses.Product;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<Product> {

	private static final String TAG = "ListItemAdapter";
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
		
		//Setting the onClickListener for the button in the list item
		Button checkButton = (Button) listItem.findViewById(R.id.checkButton);
		checkButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.v(TAG, "testi");
			}
		});
		
		return listItem;
	}

	static class ListItemHolder {
		TextView title;
	}
}
