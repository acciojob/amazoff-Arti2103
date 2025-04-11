package com.driver;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class DeliveryPartner {

    @Id
    private String id;

    private int numberOfOrders;

    public DeliveryPartner() {
        // Default constructor for JPA
    }

    public DeliveryPartner(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("DeliveryPartner ID cannot be null or empty");
        }
        this.id = id;
        this.numberOfOrders = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        if (numberOfOrders == null || numberOfOrders < 0) {
            throw new IllegalArgumentException("Number of orders must be a non-negative integer");
        }
        this.numberOfOrders = numberOfOrders;
    }
}
