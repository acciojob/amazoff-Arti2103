package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        service.addOrder(order);
        return new ResponseEntity<>("Order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId) {
        service.addPartner(partnerId);
        return new ResponseEntity<>("Partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> assignOrder(@RequestParam String orderId, @RequestParam String partnerId) {
        service.assignOrderToPartner(orderId, partnerId);
        return new ResponseEntity<>("Order assigned to partner", HttpStatus.OK);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return new ResponseEntity<>(service.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getPartnerById(partnerId), HttpStatus.OK);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCount(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getOrderCountByPartnerId(partnerId), HttpStatus.OK);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getOrdersByPartnerId(partnerId), HttpStatus.OK);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders() {
        return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getUnassignedOrderCount() {
        return new ResponseEntity<>(service.getCountOfUnassignedOrders(), HttpStatus.OK);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{time}/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfter(@PathVariable String time, @PathVariable String partnerId) {
        return new ResponseEntity<>(service.getCountOfOrdersLeftAfterTime(time, partnerId), HttpStatus.OK);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTime(@PathVariable String partnerId) {
        return new ResponseEntity<>(service.getLastDeliveryTime(partnerId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartner(@PathVariable String partnerId) {
        service.deletePartnerById(partnerId);
        return new ResponseEntity<>("Partner deleted and orders unassigned", HttpStatus.OK);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        service.deleteOrderById(orderId);
        return new ResponseEntity<>("Order deleted and unassigned", HttpStatus.OK);
    }

    // Used by test cases (non-mapped methods)
    public void addOrderPartnerPair(String orderId, String partnerId) {
        service.assignOrderToPartner(orderId, partnerId);
    }
}
