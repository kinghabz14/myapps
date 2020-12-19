package com.myapp.groceryapp;

public class ItemData {
    String itemName;
    String itemAmount;

    public ItemData (String itemName, String itemAmount) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;

    }

    public String getItemName () {
        return itemName;
    }

    public void setItemName (String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount () {
        return itemAmount;
    }

    public void setItemAmount (String itemAmount) {
        this.itemAmount = itemAmount;
    }

}
