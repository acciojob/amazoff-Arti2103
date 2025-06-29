package com.driver;

public class DeliveryPartner {

    private String partnerId;
    private int numberOfOrders;

    public DeliveryPartner() {}

    public DeliveryPartner(String partnerId) {
        this.partnerId = partnerId;
        this.numberOfOrders = 0;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    // Required for test cases
    public String getId() {
        return partnerId;
    }
}
