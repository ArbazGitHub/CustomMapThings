package com.techno.googlemap.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class LatLongItem implements Serializable{

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("state")
	private String state;

	@SerializedName("longitude")
	private double longitude;

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
}