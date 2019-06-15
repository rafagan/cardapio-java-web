package com.senac.pedro.model;

public class OrderWrapper {
    private Order order;


    public OrderWrapper(Order order) {
        this.order = order;
    }

    public OrderWrapper() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderWrapper{" +
                "order=" + order +
                '}';
    }
}
