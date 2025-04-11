package com.driver;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    private String id;

    private String deliveryTime; // make this String

    public Order() {
        // Default constructor
    }

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

    // Optional: Method to get deliveryTime in minutes
    public int getDeliveryTimeInMinutes() {
        String[] parts = deliveryTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
