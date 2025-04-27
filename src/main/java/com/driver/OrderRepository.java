package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository() {
        this.orderMap = new HashMap<>();
        this.partnerMap = new HashMap<>();
        this.partnerToOrderMap = new HashMap<>();
        this.orderToPartnerMap = new HashMap<>();
    }

    public void saveOrder(Order order) {
        orderMap.put(order.getId(), order);  // Save the order by ID
    }

    public void savePartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));  // Create and save a new partner
    }

    public void saveOrderPartnerMap(String orderId, String partnerId) {
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)) {
            partnerToOrderMap
                    .computeIfAbsent(partnerId, k -> new HashSet<>())
                    .add(orderId);  // Add the order to partner's list
            orderToPartnerMap.put(orderId, partnerId);  // Assign the order to the partner
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);  // Increase partner's order count
        }
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orderMap.get(orderId));  // Returns Optional.empty() if not found
    }

    public Optional<DeliveryPartner> findPartnerById(String partnerId) {
        return Optional.ofNullable(partnerMap.get(partnerId));  // Returns Optional.empty() if not found
    }

    public Integer findOrderCountByPartnerId(String partnerId) {
        if (partnerToOrderMap.containsKey(partnerId)) {
            return partnerToOrderMap.get(partnerId).size();  // Return the count of orders for the partner
        }
        return 0;  // No orders assigned to the partner
    }

    public List<String> findOrdersByPartnerId(String partnerId) {
        if (partnerToOrderMap.containsKey(partnerId)) {
            return new ArrayList<>(partnerToOrderMap.get(partnerId));  // Return list of order IDs for the partner
        }
        return Collections.emptyList();  // No orders assigned to the partner
    }

    public List<String> findAllOrders() {
        return new ArrayList<>(orderMap.keySet());  // Return all order IDs
    }

    public void deletePartner(String partnerId) {
        if (partnerMap.containsKey(partnerId)) {
            Set<String> orders = partnerToOrderMap.get(partnerId);
            if (orders != null) {
                // Move all orders from this partner to unassigned orders
                for (String orderId : orders) {
                    orderToPartnerMap.remove(orderId);  // Remove the partner from the order
                }
            }
            partnerMap.remove(partnerId);  // Remove the partner
            partnerToOrderMap.remove(partnerId);  // Remove partner's order mapping
        }
    }

    public void deleteOrder(String orderId) {
        if (orderMap.containsKey(orderId)) {
            String partnerId = orderToPartnerMap.remove(orderId);  // Get the partner ID associated with the order
            if (partnerId != null) {
                partnerToOrderMap.get(partnerId).remove(orderId);  // Remove the order from the partner's order list
                DeliveryPartner partner = partnerMap.get(partnerId);
                if (partner != null) {
                    partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);  // Decrease the order count of the partner
                }
            }
            orderMap.remove(orderId);  // Remove the order
        }
    }

    public Integer findCountOfUnassignedOrders() {
        int count = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderToPartnerMap.containsKey(orderId)) {
                count++;  // Increment count for unassigned orders
            }
        }
        return count;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId) {
        int count = 0;
        if (partnerToOrderMap.containsKey(partnerId)) {
            HashSet<String> orders = partnerToOrderMap.get(partnerId);
            for (String orderId : orders) {
                Order order = orderMap.get(orderId);
                if (order != null) {
                    String orderTime = order.getDeliveryTimeAsString();
                    if (orderTime.compareTo(timeString) > 0) {
                        count++;  // Increment count if the order time is after the given time
                    }
                }
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId) {
        String lastTime = "00:00";  // Default value if no orders found
        if (partnerToOrderMap.containsKey(partnerId)) {
            HashSet<String> orders = partnerToOrderMap.get(partnerId);
            for (String orderId : orders) {
                Order order = orderMap.get(orderId);
                if (order != null) {
                    String orderTime = order.getDeliveryTimeAsString();
                    if (orderTime.compareTo(lastTime) > 0) {
                        lastTime = orderTime;  // Update last delivery time if the order time is later
                    }
                }
            }
        }
        return lastTime;  // Return the last delivery time
    }
}
