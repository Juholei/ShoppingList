package com.rdsd.shoppinglist.DataClasses;

public class LocationInfo {
	public int getProductId() {
		return productId;
	}

	public LocationInfo(int productId, String latitude, String longitude) {
		this.productId = productId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setProductName(int productName) {
		this.productId = productName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	private int productId;
	private String latitude;
	private String longitude;

}
