package org.cham.postgres.tradeservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.tradeservice.domain.Order;
import org.cham.postgres.tradeservice.service.OrderService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.cham.postgres.tradeservice.domain.OrderType.BUY;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class OrderControllerUnitTest {
    private OrderService orderService = mock(OrderService.class);

    private OrderController orderController = new OrderController();

    LinkedList<Order> testOrderList = new LinkedList<>(List.of(
            new Order(9L, 9, "Steve Jobs", 1, 100, LocalDateTime.now(), BUY, 1030),
            new Order(10L, 9, "Steve Jobs", 1, 100, LocalDateTime.now(), BUY, 1234)
    ));

    public OrderControllerUnitTest(){
        orderController.setOrderService(orderService);
    }

    @Test
    void createOrder() {
        when(orderService.addOrder(any(Order.class))).thenReturn(anyLong());
        Long orderId = orderController.createOrder(testOrderList.getFirst());
        assertNotNull(orderId);
    }

    @Test
    void getAllOrders() {
        when(orderService.getAllOrders()).thenReturn(testOrderList);
        List<Order> orderList = orderController.getAllOrders();
        assertEquals(2, orderList.size());
        assertEquals(9L, orderList.get(0).getId());
        assertEquals("Steve Jobs", orderList.get(1).getCustomerName());
        assertEquals(BUY, orderList.get(1).getType());
    }

    @Test
    void getOrderById(){
        when(orderService.getOrderById(anyLong())).thenReturn(testOrderList.stream().findFirst());
        Optional <Order> testOrder = orderController.getOrderById("9");
        assertEquals(9L, testOrder.get().getId());
        assertEquals("Steve Jobs", testOrder.get().getCustomerName());
        assertEquals(BUY, testOrder.get().getType());
        assertEquals(9, testOrder.get().getCustomerId());
    }

    @Test
    void getOrdersByCustomerId() {
        when(orderService.getOrdersByCustomerId(anyInt())).thenReturn(testOrderList.stream().toList());
        List<Order> testOrderList = orderController.getOrdersByCustomerId("9");
        assertEquals(2, testOrderList.size());
        assertEquals(9L, testOrderList.get(0).getId());
        assertEquals("Steve Jobs", testOrderList.get(1).getCustomerName());
        assertEquals(BUY, testOrderList.get(1).getType());
    }
}