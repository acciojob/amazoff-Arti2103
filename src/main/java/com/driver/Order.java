package com.driver;

public class Order {

    private final String id;
    private final int deliveryTime;

    public Order(String id, String deliveryTime) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (deliveryTime == null || deliveryTime.isEmpty()) {
            throw new IllegalArgumentException("Delivery time cannot be null or empty");
        }

        this.id = id;
        this.deliveryTime = convertToMinutes(deliveryTime);
    }

    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:MM");
        }
        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numbers in delivery time", e);
        }
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
