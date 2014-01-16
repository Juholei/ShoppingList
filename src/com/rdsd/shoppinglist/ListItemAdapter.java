package com.rdsd.shoppinglist;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationClient;
import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;

public class ListItemAdapter extends ArrayAdapter<Product> {

	private static final String TAG = "ListItemAdapter";
	private Context context;
	private int layoutResourceId;
	private ShoppingList shoppingList = null;
	private LocationClient lc;
	private SQLiteHelper db;

	public ListItemAdapter(Context context, int resource,
			ShoppingList shoppingList, LocationClient lc, SQLiteHelper db) {
		super(context, resource, shoppingList.getContents());
		this.layoutResourceId = resource;
		this.context = context;
		this.shoppingList = shoppingList;
		this.lc = lc;
		this.db = db;
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

		holder.title
				.setText(shoppingList.getContents().get(position).getName());
		holder.product = shoppingList.getContents().get(position);

		// Setting the onClickListener for the button in the list item
		Button checkButton = (Button) listItem.findViewById(R.id.checkButton);
		checkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v(TAG, "testi");

				ListItemHolder holder = (ListItemHolder) ((View) v.getParent())
						.getTag();
				Location currentLoc = lc.getLastLocation();
				if (currentLoc == null) {
					currentLoc = new Location(TAG);
					currentLoc.setLatitude(0);
					currentLoc.setLongitude(0);
				}
				Product p = holder.product;
				shoppingList.removeProduct(p);
				Log.v(TAG,
						"product bought at location : Latitude "
								+ currentLoc.getLatitude() + " longitude "
								+ currentLoc.getLongitude());
				db.moveFromShoppingListToBoughtProducts(p, currentLoc);
				notifyDataSetChanged();
			}
		});

		return listItem;
	}

	static class ListItemHolder {
		TextView title;
		Product product;
	}
}
