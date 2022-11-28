package org.cham.postgres.tradeservice.service;

import org.cham.postgres.tradeservice.Repository.OrderRepository;
import org.cham.postgres.tradeservice.domain.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.cham.postgres.tradeservice.domain.OrderType.BUY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderServiceUnitTest {
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private OrderService orderService = new OrderService();

    private static final String DB_ERROR_MESSAGE = "Error while accessing the Postgres database";
    Order testOrder = new Order(9L, 9, "Steve Jobs", 1, 100,
            LocalDateTime.now(), BUY, 1030);

    LinkedList<Order> testOrderList = new LinkedList<>(List.of(
            new Order(9L, 9, "Steve Jobs", 1, 100, LocalDateTime.now(), BUY, 1030),
            new Order(10L, 9, "Steve Jobs", 1, 100, LocalDateTime.now(), BUY, 1234)
    ));

    public OrderServiceUnitTest(){
        orderService.setOrderRepository(orderRepository);
    }

    @Test
    void addOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(any());
        Long orderId = orderService.addOrder(testOrder);
        assertNotNull(orderId);
        assertEquals(9, orderId);
    }

    @Test
    void shouldTestDbErrorWhileSavingOrder(){
        Mockito.doThrow(new DataAccessException(DB_ERROR_MESSAGE){}).when(orderRepository).save(any());
        assertThrows(RuntimeException.class, () -> orderService.addOrder(testOrder));
    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(testOrderList);
        List<Order> orderList = orderService.getAllOrders();
        assertEquals(2, orderList.size());
        assertEquals(9L, orderList.get(0).getId());
        assertEquals("Steve Jobs", orderList.get(1).getCustomerName());
        assertEquals(BUY, orderList.get(1).getType());
    }

    @Test
    void shouldTestDbErrorWhileGettingAllOrders(){
        Mockito.doThrow(new DataAccessException(DB_ERROR_MESSAGE){}).when(orderRepository).findAll();
        assertThrows(RuntimeException.class, () -> {
            orderService.getAllOrders();
        });
    }

    @Test
    void getOrderById() {
        when(orderRepository.findById(anyLong())).thenReturn(testOrderList.stream().findFirst());
        Optional<Order> testOrder = orderService.getOrderById(9L);
        assertEquals(9L, testOrder.get().getId());
        assertEquals("Steve Jobs", testOrder.get().getCustomerName());
        assertEquals(BUY, testOrder.get().getType());
        assertEquals(9, testOrder.get().getCustomerId());
    }

    @Test
    void shouldTestDbErrorWhileGetOrderById(){
        Mockito.doThrow(new DataAccessException(DB_ERROR_MESSAGE){}).when(orderRepository).findById(anyLong());
        assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });
    }

    @Test
    void getOrdersByCustomerId() {
        when(orderRepository.getOrdersByCustomerId(anyInt())).thenReturn(testOrderList.stream().toList());
        List<Order> testOrderList = orderService.getOrdersByCustomerId(9);
        assertEquals(2, testOrderList.size());
        assertEquals(9L, testOrderList.get(0).getId());
        assertEquals("Steve Jobs", testOrderList.get(1).getCustomerName());
        assertEquals(BUY, testOrderList.get(1).getType());
    }

    @Test
    void shouldTestDbErrorWhileGetORderByCustomerId(){
        Mockito.doThrow(new DataAccessException(DB_ERROR_MESSAGE){}).when(orderRepository).getOrdersByCustomerId(anyInt());
        assertThrows(RuntimeException.class, () -> {
            orderService.getOrdersByCustomerId(1);
        });
    }
}