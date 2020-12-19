package com.myapp.groceryapp.network;

import com.myapp.groceryapp.Data;

public class NotificationSender {

    public Data data;
    public String to;

    public NotificationSender (Data data, String to) {
        this.data = data;
        this.to = to;

    }
}
