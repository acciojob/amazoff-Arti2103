package com.driver.test;

import com.driver.Application;
import com.driver.Order;
import com.driver.DeliveryPartner;
import com.driver.OrderController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(OrderAnnotation.class)
public class TestCases {

    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        // Initialize the controller
        orderController = new OrderController();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testAddOrder() {
        Order order = new Order("order1", "First order");
        var response = orderController.addOrder(order);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testAddPartner() {
        // Add a new delivery partner
        var response = orderController.addPartner("partner1");
        assertEquals("New delivery partner added successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testAddOrderPartnerPair() {
        // Add an order-partner pair
        var response = orderController.addOrderPartnerPair("order1", "partner1");
        assertEquals("New order-partner pair added successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void testGetOrderById() {

        Order order = new Order("order1", "First order");
        orderController.addOrder(order);


        ResponseEntity<Order> response = orderController.getOrderById("order1");

        assertNotNull(response.getBody()); // Yeh ab pass hoga
        assertEquals("order1", response.getBody().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    @org.junit.jupiter.api.Order(5)
    public void testGetPartnerById() {

        DeliveryPartner partner = new DeliveryPartner("partner1");
        partner.setNumberOfOrders(5); // Optionally setting the number of orders


        orderController.getPartnerMap().put(partner.getId(), partner);


        var response = orderController.getPartnerById("partner1");


        assertNotNull(response.getBody());
        assertEquals("partner1", response.getBody().getId());
    }



    @Test
    @org.junit.jupiter.api.Order(9)
    public void testGetCountOfUnassignedOrders() {
        // Get the count of unassigned orders
        var response = orderController.getCountOfUnassignedOrders();
        assertEquals(0, response.getBody());  // Assuming no unassigned orders at the start
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    public void testDeleteOrderById() {
        // Delete an order by its ID
        var response = orderController.deleteOrderById("order1");
        assertEquals("order1 removed successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    public void testDeletePartnerById() {
        // Delete a delivery partner by their ID
        var response = orderController.deletePartnerById("partner1");
        assertEquals("partner1 removed successfully", response.getBody());
    }
}
