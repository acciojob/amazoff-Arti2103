package com.driver.test;

import com.driver.Order;
import com.driver.DeliveryPartner;
import com.driver.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private OrderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new OrderRepository();
    }

    @Test
    void testAddAndGetOrder() {
        Order order = new Order("order1", "12:30");
        repository.addOrder(order);

        Order result = repository.getOrderById("order1");
        assertNotNull(result);
        assertEquals("12:30", result.getDeliveryTime());
    }

    @Test
    void testAddAndGetPartner() {
        repository.addPartner("partner1");
        DeliveryPartner result = repository.getPartnerById("partner1");

        assertNotNull(result);
        assertEquals("partner1", result.getPartnerId());
    }

    @Test
    void testAssignOrderToPartner() {
        Order order = new Order("order1", "09:00");
        repository.addOrder(order);
        repository.addPartner("partner1");
        repository.assignOrderToPartner("order1", "partner1");

        List<String> assignedOrders = repository.getOrdersByPartnerId("partner1");
        assertTrue(assignedOrders.contains("order1"));
    }

    @Test
    void testGetOrderCountByPartnerId() {
        repository.addPartner("partner1");
        repository.addOrder(new Order("order1", "09:00"));
        repository.addOrder(new Order("order2", "11:00"));

        repository.assignOrderToPartner("order1", "partner1");
        repository.assignOrderToPartner("order2", "partner1");

        assertEquals(2, repository.getOrderCountByPartnerId("partner1"));
    }

    @Test
    void testGetAllOrders() {
        repository.addOrder(new Order("order1", "08:00"));
        repository.addOrder(new Order("order2", "09:00"));

        List<String> allOrders = repository.getAllOrders();
        assertEquals(2, allOrders.size());
    }

    @Test
    void testGetUnassignedOrderCount() {
        repository.addOrder(new Order("order1", "08:00"));
        repository.addOrder(new Order("order2", "09:00"));
        repository.addPartner("partner1");
        repository.assignOrderToPartner("order1", "partner1");

        int unassigned = repository.getUnassignedOrderCount();
        assertEquals(1, unassigned);
    }

    @Test
    void testGetOrdersLeftAfterTime() {
        repository.addPartner("partner1");
        repository.addOrder(new Order("order1", "10:00"));
        repository.addOrder(new Order("order2", "15:30"));
        repository.assignOrderToPartner("order1", "partner1");
        repository.assignOrderToPartner("order2", "partner1");

        int count = repository.getOrdersLeftAfterTime("11:00", "partner1");
        assertEquals(1, count);
    }

    @Test
    void testGetLastDeliveryTime() {
        repository.addPartner("partner1");
        repository.addOrder(new Order("order1", "10:00"));
        repository.addOrder(new Order("order2", "20:45"));
        repository.assignOrderToPartner("order1", "partner1");
        repository.assignOrderToPartner("order2", "partner1");

        String lastTime = repository.getLastDeliveryTime("partner1");
        assertEquals("20:45", lastTime);
    }

    @Test
    void testDeletePartnerUnassignsOrders() {
        repository.addPartner("partner1");
        repository.addOrder(new Order("order1", "10:00"));
        repository.assignOrderToPartner("order1", "partner1");

        repository.deletePartnerById("partner1");

        assertNull(repository.getPartnerById("partner1"));
        assertEquals(1, repository.getUnassignedOrderCount());
    }

    @Test
    void testDeleteOrderUnassignsFromPartner() {
        repository.addPartner("partner1");
        repository.addOrder(new Order("order1", "11:00"));
        repository.assignOrderToPartner("order1", "partner1");

        repository.deleteOrderById("order1");

        assertNull(repository.getOrderById("order1"));
        assertEquals(0, repository.getOrderCountByPartnerId("partner1"));
    }
}
