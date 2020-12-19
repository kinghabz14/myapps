package com.myapp.groceryapp;

public class Data {

    public String Title;
    public String ItemList;

    public Data (String title, String itemList) {

        Title = title;
        ItemList = itemList;
    }

    public Data () {

    }

    public String getTitle () {
        return Title;
    }

    public void setTitle (String title) {
        Title = title;
    }

    public String getItemList () {
        return ItemList;
    }

    public void setMessage(String itemList) {
        ItemList = itemList;
    }

}
