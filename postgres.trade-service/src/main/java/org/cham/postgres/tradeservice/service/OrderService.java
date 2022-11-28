package org.cham.postgres.tradeservice.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.tradeservice.Repository.OrderRepository;
import org.cham.postgres.tradeservice.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Setter
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    private static final String DB_ERROR_MESSAGE = "Error while accessing the Postgres database";
    public Long addOrder(Order order){
        log.info("Inside OrderService.addOrder");
        log.info("Order details passed are " + order.toString());
        try{
            orderRepository.save(order);
            return order.getId();
        }catch(DataAccessException e){
            log.error("Error while accessing the database" + e.getMessage());
            throw new RuntimeException(DB_ERROR_MESSAGE);
        }
    }
    public List<Order> getAllOrders(){
        log.info("Inside OrderService.getAllOrders");
        List<Order> orderList;
        try{
            orderList= orderRepository.findAll();
        }catch(DataAccessException e){
            log.error("Error while accessing the database for getAllOrders" + e.getMessage());
            throw new RuntimeException(DB_ERROR_MESSAGE);
        }
        return orderList;
    }
    public Optional<Order> getOrderById(Long id){
        log.info("Inside OrderService.getOrderById and orderId = " + id);
        Optional<Order> order;
        try{
            order = orderRepository.findById(id);
        }catch(DataAccessException e){
            log.error("Error while accessing the database for getOrderById" + e.getMessage());
            throw new RuntimeException(DB_ERROR_MESSAGE);
        }
        return order;
    }
    public List<Order>  getOrdersByCustomerId(Integer customerId){
        log.info("Inside OrderService.getOrdersByCustomerId and customerId = " + customerId);
        List<Order> orders;
        try{
            orders = orderRepository.getOrdersByCustomerId(customerId);
        }catch(DataAccessException e){
            log.error("Error while accessing the database for getOrdersByCustomerId" + e.getMessage());
            throw new RuntimeException(DB_ERROR_MESSAGE);
        }
        return orders;
    }
}
