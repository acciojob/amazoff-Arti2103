package com.driver.test;

import com.driver.Application;
import com.driver.DeliveryPartner;
import com.driver.Order;
import com.driver.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {
    @Autowired
    private OrderController orderController;
    @BeforeEach
    void setup() {
        // This method runs before each test case
    }

    @Test
    void sampleTest() {
        // Sample test case
    }
    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddOrder() {
        Order order = new Order("O1", "12:30");
        ResponseEntity<String> response = orderController.addOrder(order);
        assertEquals("New order added successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testAddPartner() {
        ResponseEntity<String> response = orderController.addPartner("P1");
        assertEquals("New delivery partner added successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testAddOrderPartnerPair() {
        ResponseEntity<String> response = orderController.addOrderPartnerPair("O1", "P1");
        assertEquals("New order-partner pair added successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testGetOrderById() {
        ResponseEntity<Order> response = orderController.getOrderById("O1");
        assertNotNull(response.getBody());
        assertEquals("O1", response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testGetPartnerById() {
        ResponseEntity<DeliveryPartner> response = orderController.getPartnerById("P1");
        assertNotNull(response.getBody());
        assertEquals("P1", response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void testGetAllOrders() {
        ResponseEntity<List<String>> response = orderController.getAllOrders();
        assertTrue(response.getBody().contains("O1"));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void testGetOrderCountByPartnerId() {
        ResponseEntity<Integer> response = orderController.getOrderCountByPartnerId("P1");
        assertEquals(1, response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void testDeleteOrderById() {
        ResponseEntity<String> response = orderController.deleteOrderById("O1");
        assertEquals("O1 removed successfully", response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void testDeletePartnerById() {
        ResponseEntity<String> response = orderController.deletePartnerById("P1");
        assertEquals("P1 removed successfully", response.getBody());
    }
}

