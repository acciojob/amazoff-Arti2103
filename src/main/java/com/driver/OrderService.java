package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order){
        if (order != null) {
            orderRepository.saveOrder(order);
        }
    }

    public void addPartner(String partnerId) {
        if (partnerId != null && !partnerId.trim().isEmpty()) {
            orderRepository.savePartner(partnerId);
        }
    }

    public void createOrderPartnerPair(String orderId, String partnerId) {
        if (orderId != null && partnerId != null) {
            orderRepository.saveOrderPartnerMap(orderId, partnerId);
        }
    }

    public Order getOrderById(String orderId){
        if (orderId == null) return null;
        return orderRepository.findOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        if (partnerId == null) return null;
        return orderRepository.findPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        if (partnerId == null) return 0;
        Integer count = orderRepository.findOrderCountByPartnerId(partnerId);
        return (count != null) ? count : 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        if (partnerId == null) return new ArrayList<>();
        List<String> orders = orderRepository.findOrdersByPartnerId(partnerId);
        return (orders != null) ? orders : new ArrayList<>();
    }

    public List<String> getAllOrders(){
        List<String> allOrders = orderRepository.findAllOrders();
        return (allOrders != null) ? allOrders : new ArrayList<>();
    }

    public void deletePartner(String partnerId){
        if (partnerId != null) {
            orderRepository.deletePartner(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        if (orderId != null) {
            orderRepository.deleteOrder(orderId);
        }
    }

    public Integer getCountOfUnassignedOrders(){
        Integer count = orderRepository.findCountOfUnassignedOrders();
        return (count != null) ? count : 0;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        if (time == null || partnerId == null) return 0;
        Integer count = orderRepository.findOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        return (count != null) ? count : 0;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        if (partnerId == null) return null;
        return orderRepository.findLastDeliveryTimeByPartnerId(partnerId);
    }
}
