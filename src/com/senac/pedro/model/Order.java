package com.senac.pedro.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String name;
    private String address;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String created;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String name, String address, String phoneNumber, String created, List<OrderItem> items) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.created = created;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", created='" + created + '\'' +
                ", items=" + items +
                '}';
    }
}
