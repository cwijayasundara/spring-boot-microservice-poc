package org.cham.postgres.tradeservice.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.tradeservice.Repository.OrderRepository;
import org.cham.postgres.tradeservice.domain.Order;
import org.cham.postgres.tradeservice.domain.OrderType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class OrderControllerIntegrationTest {
    @LocalServerPort
    @Getter
    private int port;
    private static long orderId = 0;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        LinkedList<Order> buyOrders = new LinkedList<>(List.of(
                new Order(++orderId, 1, "Elon Mask", 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
                new Order(++orderId, 2, "Joe Bloggs", 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
                new Order(++orderId, 3, "Tom Johns", 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
                new Order(++orderId, 4, "Gramme King",1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
                new Order(++orderId, 5, "Jim Carry", 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
                new Order(++orderId, 11, "Bob Marley", 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)
        ));
        orderRepository.saveAll(buyOrders);
    }

    @AfterEach
    void cleanUp(){
        orderRepository.deleteAll();
    }

    @Test
    void shouldTestCreateOrder() {
        Order testOrder = new Order(12L, 11, "Bob Marley", 1, 100,
            LocalDateTime.now(), OrderType.BUY, 1050);
        ResponseEntity<String> responseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/api/orders", testOrder, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(responseEntity.getBody().contains("12"));
    }

    @Test
    void shouldTestGetOrdersByCustomerId() throws MalformedURLException {
        URL baseUrl = new URL("http://localhost:" + port + "/api/orders?customerId=1");
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().contains("Elon Mask"));
        assertTrue(response.getBody().contains("BUY"));
        assertTrue(response.getBody().contains("1000"));
    }

    @Test
    void shouldTestGetOrdersByCustomerIdNegative() throws MalformedURLException {
        URL baseUrl = new URL("http://localhost:" + port + "/api/orders?customerId=9");
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldTestGetAllOrders() throws MalformedURLException{
        URL baseUrl = new URL("http://localhost:" + port + "/api/orders/all");
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().contains("Gramme King"));
        assertTrue(response.getBody().contains("BUY"));
        assertTrue(response.getBody().contains("1050"));
    }

    @Test
    void shouldTestGetOrderByOrderId() throws MalformedURLException {
        URL baseUrl = new URL("http://localhost:" + port + "/api/orders/5");
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldTestGetOrderByOrderIdNegative() throws MalformedURLException{
        URL baseUrl = new URL("http://localhost:" + port + "/api/orders/10");
        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}