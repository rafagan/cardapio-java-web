package com.senac.pedro.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.pedro.model.Order;
import com.senac.pedro.model.OrderWrapper;

public class OrderJSONSerializer {
    public Order parse(String data) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        OrderWrapper order = gson.fromJson(data, OrderWrapper.class);
        System.out.println(order);
        return order.getOrder();
    }
}
