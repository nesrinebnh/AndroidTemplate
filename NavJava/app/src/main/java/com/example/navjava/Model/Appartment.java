package com.example.navjava.Model;

import java.util.List;

public class Appartment {
    String id,lat,lng,name,ref,vicinity,type,openDays,price,capacity,couvert;

    public Appartment(){}

    public Appartment(String name, String type, String lat, String lng, String address, String prix){
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.ref = address;
        this.price = prix;
    }

    public Appartment(String name){
        this.name = name;
    }

    public Appartment(String lat, String lng, String name, String ref, String vicinity, String type,String price) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.ref = ref;
        this.vicinity = vicinity;
        this.type = type;
        this.price = price;

    }


    public Appartment(String id,String lat, String lng, String name, String ref, String vicinity,  String type,String price) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.ref = ref;
        this.vicinity = vicinity;
        this.type = type;
        this.id=id;
        this.price = price;
    }

    public Appartment(String id,String lat, String lng, String name){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.id=id;
    }


    public Appartment(String lat, String lng, String name){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



}
