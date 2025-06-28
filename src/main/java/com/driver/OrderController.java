package com.driver;

import com.driver.Order;
import com.driver.DeliveryPartner;
import com.driver.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // 1. Add an Order
    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        service.addOrder(order);
        return new ResponseEntity<>("Order added successfully", HttpStatus.CREATED);
    }

    // 2. Add a Delivery Partner
    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId) {
        service.addPartner(partnerId);
        return new ResponseEntity<>("Partner added successfully", HttpStatus.CREATED);
    }

    // 3. Assign an Order to a Partner
    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> assignOrderToPartner(@RequestParam String orderId, @RequestParam String partnerId) {
        service.assignOrderToPartner(orderId, partnerId);
        return new ResponseEntity<>("Order assigned to partner", HttpStatus.OK);
    }

    // 4. Get Order by orderId
    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return new ResponseEntity<>(service.getOrderById(orderId), HttpStatus.OK);
    }

    // 5. Get Partner by partnerId
    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getPartnerById(partnerId), HttpStatus.OK);
    }

    // 6. Get number of orders assigned to given partnerId
    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getOrderCountByPartnerId(partnerId), HttpStatus.OK);
    }

    // 7. Get list of all orders assigned to a partner
    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getOrdersByPartnerId(partnerId), HttpStatus.OK);
    }

    // 8. Get list of all orders
    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders() {
        return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
    }

    // 9. Get count of unassigned orders
    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders() {
        return new ResponseEntity<>(service.getCountOfUnassignedOrders(), HttpStatus.OK);
    }

    // 10. Get count of orders left after given time for a partner
    @GetMapping("/get-count-of-orders-left-after-given-time/{time}/{partnerId}")
    public ResponseEntity<Integer> getCountOfOrdersLeftAfterTime(@PathVariable String time, @PathVariable String partnerId) {
        return new ResponseEntity<>(service.getCountOfOrdersLeftAfterTime(time, partnerId), HttpStatus.OK);
    }

    // 11. Get last delivery time by partner
    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTime(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getLastDeliveryTime(partnerId), HttpStatus.OK);
    }

    // 12. Delete partner and unassign their orders
    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId) {
        service.deletePartnerById(partnerId);
        return new ResponseEntity<>("Partner deleted and orders unassigned", HttpStatus.OK);
    }

    // 13. Delete order and unassign from partner
    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId) {
        service.deleteOrderById(orderId);
        return new ResponseEntity<>("Order deleted and unassigned", HttpStatus.OK);
    }
}
