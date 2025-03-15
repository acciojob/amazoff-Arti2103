package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            partnerToOrderMap.putIfAbsent(partnerId, new HashSet<>());
            partnerToOrderMap.get(partnerId).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);


            partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        System.out.println("Finding order by ID: " + orderId);
        return orderMap.getOrDefault(orderId, null);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        System.out.println("Fetching partner: " + partnerId);
        return partnerMap.getOrDefault(partnerId, null);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here

        System.out.println("Checking order count for partner: " + partnerId);
        if (!partnerToOrderMap.containsKey(partnerId)) return 0;

        return partnerToOrderMap.get(partnerId).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        System.out.println("Fetching orders for partner: " + partnerId);
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(partnerToOrderMap.get(partnerId));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        System.out.println("Fetching all orders...");
        return orderMap.isEmpty() ? new ArrayList<>() : new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
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

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        String partnerId = orderToPartnerMap.remove(orderId);
        if (partnerId != null) {
            partnerToOrderMap.get(partnerId).remove(orderId);
        }
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        System.out.println("Checking unassigned orders...");
        if (orderMap == null || orderMap.isEmpty()) return 0;

        return (int) orderMap.keySet().stream()
                .filter(orderId -> !orderToPartnerMap.containsKey(orderId))
                .count();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int givenTime = convertTimeToMinutes(timeString);

        return (int) partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).stream()
                .map(orderMap::get)
                .filter(order -> order.getDeliveryTime() > givenTime)
                .count();
    }
    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId) {
        if (!partnerToOrderMap.containsKey(partnerId) || partnerToOrderMap.get(partnerId).isEmpty()) {
            System.out.println("No orders found for partner: " + partnerId);
            return "00:00"; // Default value if no deliveries
        }

        int maxMinutes = partnerToOrderMap.get(partnerId).stream()
                .map(orderMap::get)
                .filter(Objects::nonNull)
                .mapToInt(Order::getDeliveryTime)
                .max()
                .orElse(0);

        return String.format("%02d:%02d", maxMinutes / 60, maxMinutes % 60);
    }
}