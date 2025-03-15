package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public class OrderRepository {

    private final HashMap<String, Order> orderMap;
    private final HashMap<String, DeliveryPartner> partnerMap;
    private final HashMap<String, HashSet<String>> partnerToOrderMap;
    private final HashMap<String, String> orderToPartnerMap;

    @Autowired
    public OrderRepository() {
        this.orderMap = new HashMap<>();
        this.partnerMap = new HashMap<>();
        this.partnerToOrderMap = new HashMap<>();
        this.orderToPartnerMap = new HashMap<>();
    }

    public void saveOrder(Order order) {
        if (order != null) {
            orderMap.put(order.getId(), order);
        }
    }

    public void savePartner(String partnerId) {
        if (partnerId != null && !partnerId.isEmpty()) {
            partnerMap.put(partnerId, new DeliveryPartner(partnerId));
        }
    }

    public void saveOrderPartnerMap(String orderId, String partnerId) {
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)) {
            partnerToOrderMap.putIfAbsent(partnerId, new HashSet<>());
            partnerToOrderMap.get(partnerId).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);

            // Update partner's order count
            partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
        }
    }

    public Order findOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId) {
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId) {
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId) {
        if (partnerMap.containsKey(partnerId)) {
            HashSet<String> orders = partnerToOrderMap.remove(partnerId);
            if (orders != null) {
                for (String orderId : orders) {
                    orderToPartnerMap.remove(orderId);
                }
            }
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId) {
        if (orderMap.containsKey(orderId)) {
            String partnerId = orderToPartnerMap.remove(orderId);
            if (partnerId != null && partnerToOrderMap.containsKey(partnerId)) {
                partnerToOrderMap.get(partnerId).remove(orderId);
                partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
            }
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders() {
        int unassignedCount = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderToPartnerMap.containsKey(orderId)) {
                unassignedCount++;
            }
        }
        return unassignedCount;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId) {
        int givenTime = convertTimeToMinutes(timeString);
        return (int) partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).stream()
                .map(orderMap::get)
                .filter(Objects::nonNull)
                .mapToInt(Order::getDeliveryTime)
                .filter(time -> time > givenTime)
                .count();
    }

    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId) {
        int maxMinutes = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).stream()
                .map(orderMap::get)
                .filter(Objects::nonNull)
                .mapToInt(Order::getDeliveryTime)
                .max()
                .orElse(0);

        return String.format("%02d:%02d", maxMinutes / 60, maxMinutes % 60);
    }
}
