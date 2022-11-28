package org.cham.postgres.tradeservice.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.tradeservice.domain.Order;
import org.cham.postgres.tradeservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@Slf4j
@Setter
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/api/orders") // POST /api/orders
    @ResponseStatus(HttpStatus.OK)
    public Long createOrder(@RequestBody Order order){
        log.info("Inside OrderController.createOrder()", order.toString());
        return orderService.addOrder(order);
    }

    @GetMapping("/api/orders/all") //GET /api/orders
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        log.info("Inside OrderController.getAllOrders()");
        List<Order> orders = orderService.getAllOrders();
        return orders;
    }

    @GetMapping("/api/orders/{orderId}") // GET /api/orders/2
    @ResponseStatus(HttpStatus.OK)
    public Optional<Order> getOrderById(@PathVariable String orderId){
        log.info("Inside OrderController.getOrderById()" + orderId);
        Long id = Long.parseLong(orderId);
        return orderService.getOrderById(id);
    }

    @GetMapping("/api/orders") // GET /api/orders?customerId=1
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByCustomerId(@RequestParam ("customerId") String customerId){
        log.info("Inside OrderController.getOrdersByCustomerId()" + customerId);
        Integer id = Integer.parseInt(customerId);
        return orderService.getOrdersByCustomerId(id);
    }
}
