package com.driver;

import com.driver.Order;
import com.driver.DeliveryPartner;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, List<String>> partnerOrdersMap = new HashMap<>();
    private Map<String, String> orderPartnerMap = new HashMap<>();

    // Add order
    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    // Add delivery partner
    public void addPartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
        partnerOrdersMap.put(partnerId, new ArrayList<>());
    }

    // Assign order to partner
    public void assignOrderToPartner(String orderId, String partnerId) {
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)) {
            orderPartnerMap.put(orderId, partnerId);
            partnerOrdersMap.get(partnerId).add(orderId);
        }
    }

    // Get Order
    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    // Get Partner
    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    // Get all orders for a partner
    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
    }

    // Get count of orders for a partner
    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>()).size();
    }

    // Get all orders
    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    // Get count of unassigned orders
    public int getUnassignedOrderCount() {
        int count = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderPartnerMap.containsKey(orderId)) count++;
        }
        return count;
    }

    // Get orders left after a given time for a partner
    public int getOrdersLeftAfterTime(String time, String partnerId) {
        int count = 0;
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orders) {
            String deliveryTime = orderMap.get(orderId).getDeliveryTime();
            if (deliveryTime.compareTo(time) > 0) {
                count++;
            }
        }
        return count;
    }

    // Get last delivery time for a partner
    public String getLastDeliveryTime(String partnerId) {
        String lastTime = "00:00";
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orders) {
            String time = orderMap.get(orderId).getDeliveryTime();
            if (time.compareTo(lastTime) > 0) {
                lastTime = time;
            }
        }
        return lastTime;
    }

    // Delete partner and unassign their orders
    public void deletePartnerById(String partnerId) {
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orders) {
            orderPartnerMap.remove(orderId);
        }
        partnerOrdersMap.remove(partnerId);
        partnerMap.remove(partnerId);
    }

    // Delete order and unassign from partner
    public void deleteOrderById(String orderId) {
        String partnerId = orderPartnerMap.get(orderId);
        if (partnerId != null) {
            partnerOrdersMap.get(partnerId).remove(orderId);
            orderPartnerMap.remove(orderId);
        }
        orderMap.remove(orderId);
    }
}
