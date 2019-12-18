package com.cali.geohole.model;

import android.net.Uri;

import java.io.Serializable;

public class Hole implements Serializable {
    String address;
    Double length;
    Double width;
    Double height;
    Double latitude;
    Double longitude;
    // Foto
    String photo;

    public Hole(String address, Double length, Double width, Double height, Double latitude, Double longitude) {
        this.address = address;
        this.length = length;
        this.width = width;
        this.height = height;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String  getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
