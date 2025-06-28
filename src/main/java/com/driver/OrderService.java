package com.driver;

import com.driver.Order;

import com.driver.DeliveryPartner;
import com.driver.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Add an order
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    // Add a delivery partner
    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    // Assign an order to a partner
    public void assignOrderToPartner(String orderId, String partnerId) {
        orderRepository.assignOrderToPartner(orderId, partnerId);
    }

    // Get order by orderId
    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    // Get partner by partnerId
    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    // Get number of orders assigned to a partner
    public int getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    // Get all orders assigned to a partner
    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    // Get all orders in the system
    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    // Get count of unassigned orders
    public int getCountOfUnassignedOrders() {
        return orderRepository.getUnassignedOrderCount();
    }

    // Get count of orders left after given time for a partner
    public int getCountOfOrdersLeftAfterTime(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterTime(time, partnerId);
    }

    // Get last delivery time for a partner
    public String getLastDeliveryTime(String partnerId) {
        return orderRepository.getLastDeliveryTime(partnerId);
    }

    // Delete partner and unassign their orders
    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    // Delete order and unassign from partner
    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
