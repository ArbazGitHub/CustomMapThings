package com.techno.googlemap.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;


public class LatLongClusterItem implements Serializable,ClusterItem{

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("state")
	private String state;

	LatLng latLng;

	public LatLongClusterItem(double latitude, double longitude, String state) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
		this.latLng=new LatLng(latitude,longitude);
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return
			"LatLongItem{" +
			"latitude = '" + latitude + '\'' +
			",state = '" + state + '\'' +
			",longitude = '" + longitude + '\'' +
			"}";
		}

	@Override
	public LatLng getPosition() {
		return latLng;
	}

	@Override
	public String getTitle() {
		return state;
	}

	@Override
	public String getSnippet() {
		return "Snippet here";
	}
}