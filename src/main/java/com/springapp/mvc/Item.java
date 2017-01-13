package com.springapp.mvc;


public class Item {
    String name;
    Integer price;
    String about;
    Integer id;

    public Item() {

    }

    public Item(String name, Integer price, String about, Integer id) {
        this.name = name;
        this.price = price;
        this.about = about;
        this.id = id;
    }
}
