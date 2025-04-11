package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    private final HashMap<String, DeliveryPartner> partnerMap;
    private final HashMap<String, HashSet<String>> partnerToOrderMap;
    private final HashMap<String, String> orderToPartnerMap;

    @Autowired
    public OrderRepository() {
        this.partnerMap = new HashMap<>();
        this.partnerToOrderMap = new HashMap<>();
        this.orderToPartnerMap = new HashMap<>();
    }

    public void saveOrder(Order order) {
        if (order != null) {
            orderJpaRepository.save(order);
        }
    }

    public List<Order> findAllOrdersFromDB() {
        List<Order> orders = orderJpaRepository.findAll();
        return (orders != null) ? orders : new ArrayList<>();
    }

    public void savePartner(String partnerId) {
        if (partnerId != null && !partnerId.isEmpty()) {
            partnerMap.put(partnerId, new DeliveryPartner(partnerId));
        }
    }

    public void saveOrderPartnerMap(String orderId, String partnerId) {
        if (orderId == null || partnerId == null) return;

        if (orderJpaRepository.existsById(orderId) && partnerMap.containsKey(partnerId)) {
            partnerToOrderMap.putIfAbsent(partnerId, new HashSet<>());
            partnerToOrderMap.get(partnerId).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);

            DeliveryPartner partner = partnerMap.get(partnerId);
            if (partner != null) {
                partner.setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
            }
        }
    }

    public Order findOrderById(String orderId) {
        if (orderId == null) return null;
        return orderJpaRepository.findById(orderId).orElse(null);
    }

    public DeliveryPartner findPartnerById(String partnerId) {
        if (partnerId == null) return null;
        return partnerMap.getOrDefault(partnerId, null);
    }

    public Integer findOrderCountByPartnerId(String partnerId) {
        if (partnerId == null) return 0;
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId) {
        if (partnerId == null) return new ArrayList<>();
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders() {
        List<Order> orders = orderJpaRepository.findAll();
        List<String> orderIds = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                if (order != null && order.getId() != null) {
                    orderIds.add(order.getId());
                }
            }
        }
        return orderIds;
    }

    public void deletePartner(String partnerId) {
        if (partnerId == null || !partnerMap.containsKey(partnerId)) return;

        HashSet<String> orders = partnerToOrderMap.remove(partnerId);
        if (orders != null) {
            for (String orderId : orders) {
                orderToPartnerMap.remove(orderId);
            }
        }
        partnerMap.remove(partnerId);
    }

    public void deleteOrder(String orderId) {
        if (orderId == null || !orderJpaRepository.existsById(orderId)) return;

        orderToPartnerMap.remove(orderId);
        for (HashSet<String> orders : partnerToOrderMap.values()) {
            if (orders != null) {
                orders.remove(orderId);
            }
        }
        orderJpaRepository.deleteById(orderId);
    }

    public Integer findCountOfUnassignedOrders() {
        List<Order> allOrders = orderJpaRepository.findAll();
        if (allOrders == null) return 0;

        int unassigned = 0;
        for (Order order : allOrders) {
            if (order != null && !orderToPartnerMap.containsKey(order.getId())) {
                unassigned++;
            }
        }
        return unassigned;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId) {
        if (timeString == null || partnerId == null) return 0;

        int givenTime = convertTimeToMinutes(timeString);
        Set<String> orderIds = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());

        return (int) orderIds.stream()
                .map(orderId -> orderJpaRepository.findById(orderId).orElse(null))
                .filter(Objects::nonNull)
                .mapToInt(Order::getDeliveryTimeInMinutes)
                .filter(time -> time > givenTime)
                .count();
    }

    private int convertTimeToMinutes(String time) {
        try {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        } catch (Exception e) {
            return 0; // fallback in case of bad format
        }
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId) {
        if (partnerId == null) return "00:00";

        Set<String> orderIds = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());

        int maxMinutes = orderIds.stream()
                .map(orderId -> orderJpaRepository.findById(orderId).orElse(null))
                .filter(Objects::nonNull)
                .mapToInt(Order::getDeliveryTimeInMinutes)
                .max()
                .orElse(0);

        return String.format("%02d:%02d", maxMinutes / 60, maxMinutes % 60);
    }
}
