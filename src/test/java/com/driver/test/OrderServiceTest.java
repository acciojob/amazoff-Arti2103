package com.driver.test;

import com.driver.Order;
import com.driver.OrderService;
import com.driver.DeliveryPartner;
import com.driver.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrder() {
        Order order = new Order("order1", "10:30");
        orderService.addOrder(order);
        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddPartner() {
        orderService.addPartner("partner1");
        verify(orderRepository, times(1)).addPartner("partner1");
    }

    @Test
    void testAssignOrderToPartner() {
        orderService.assignOrderToPartner("order1", "partner1");
        verify(orderRepository, times(1)).assignOrderToPartner("order1", "partner1");
    }

    @Test
    void testGetOrderById() {
        Order order = new Order("order1", "10:30");
        when(orderRepository.getOrderById("order1")).thenReturn(order);
        Order result = orderService.getOrderById("order1");
        assertEquals("10:30", result.getDeliveryTime());
    }

    @Test
    void testGetPartnerById() {
        DeliveryPartner partner = new DeliveryPartner("partner1");
        when(orderRepository.getPartnerById("partner1")).thenReturn(partner);
        DeliveryPartner result = orderService.getPartnerById("partner1");
        assertEquals("partner1", result.getPartnerId());
    }

    @Test
    void testGetOrderCountByPartnerId() {
        when(orderRepository.getOrderCountByPartnerId("partner1")).thenReturn(2);
        int count = orderService.getOrderCountByPartnerId("partner1");
        assertEquals(2, count);
    }

    @Test
    void testGetOrdersByPartnerId() {
        List<String> orders = Arrays.asList("order1", "order2");
        when(orderRepository.getOrdersByPartnerId("partner1")).thenReturn(orders);
        List<String> result = orderService.getOrdersByPartnerId("partner1");
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllOrders() {
        List<String> orders = Arrays.asList("order1", "order2");
        when(orderRepository.getAllOrders()).thenReturn(orders);
        assertEquals(2, orderService.getAllOrders().size());
    }

    @Test
    void testGetCountOfUnassignedOrders() {
        when(orderRepository.getUnassignedOrderCount()).thenReturn(1);
        assertEquals(1, orderService.getCountOfUnassignedOrders());
    }

    @Test
    void testGetCountOfOrdersLeftAfterTime() {
        when(orderRepository.getOrdersLeftAfterTime("11:00", "partner1")).thenReturn(1);
        assertEquals(1, orderService.getCountOfOrdersLeftAfterTime("11:00", "partner1"));
    }

    @Test
    void testGetLastDeliveryTime() {
        when(orderRepository.getLastDeliveryTime("partner1")).thenReturn("18:45");
        assertEquals("18:45", orderService.getLastDeliveryTime("partner1"));
    }

    @Test
    void testDeletePartnerById() {
        orderService.deletePartnerById("partner1");
        verify(orderRepository, times(1)).deletePartnerById("partner1");
    }

    @Test
    void testDeleteOrderById() {
        orderService.deleteOrderById("order1");
        verify(orderRepository, times(1)).deleteOrderById("order1");
    }
}
