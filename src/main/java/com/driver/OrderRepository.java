package com.driver;

import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderRepository {

    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, List<String>> partnerOrdersMap = new HashMap<>();
    private Map<String, String> orderPartnerMap = new HashMap<>();

    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public void addPartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
        partnerOrdersMap.put(partnerId, new ArrayList<>());
    }

    public void assignOrderToPartner(String orderId, String partnerId) {
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)) {
            orderPartnerMap.put(orderId, partnerId);
            partnerOrdersMap.get(partnerId).add(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
        }
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
    }

    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>()).size();
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public int getUnassignedOrderCount() {
        int count = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderPartnerMap.containsKey(orderId)) {
                count++;
            }
        }
        return count;
    }

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

    public void deletePartnerById(String partnerId) {
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        for (String orderId : orders) {
            orderPartnerMap.remove(orderId);
        }
        partnerOrdersMap.remove(partnerId);
        partnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        String partnerId = orderPartnerMap.get(orderId);
        if (partnerId != null) {
            partnerOrdersMap.get(partnerId).remove(orderId);
            orderPartnerMap.remove(orderId);
        }
        orderMap.remove(orderId);
    }
}
