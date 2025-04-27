package com.driver;

public class Order {
    private String id;
    private String deliveryTime;  // Time in string format, e.g., "12:30"

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public String getDeliveryTimeAsString() {
        return this.deliveryTime;
    }
}
