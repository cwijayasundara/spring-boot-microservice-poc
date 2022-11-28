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
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class OrderControllerRestAssuredTest {

    @LocalServerPort
    @Getter
    private int port;

    @Autowired
    private OrderRepository orderRepository;
    private final String APPLICATION_JSON = "application/json";

    Order testOrder = new Order(11L, 1, "Bill Gates", 1, 100, LocalDateTime.now(),
            OrderType.BUY, 1000);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:";
        orderRepository.save(testOrder);
    }

    @Test
    void createOrder() {
        with().body(testOrder)
                .when()
                .contentType(APPLICATION_JSON)
                .request("POST", RestAssured.baseURI + port + "/api/orders")
                .then()
                .statusCode(200)
                .body(containsString("11"));
    }

    @Test
    void getAllOrders() {
        when().request("GET", RestAssured.baseURI + port + "/api/orders/all")
                .then().statusCode(200).body(containsString("1000"));
    }

    @Test
    void getOrderById() {
        when().request("GET", RestAssured.baseURI + port + "/api/orders/11")
                .then().statusCode(200)
                .body(not(empty()));
        when().request("GET", RestAssured.baseURI + port + "/api/orders/11")
                .then().statusCode(200)
                .body(containsString("Bill Gates"));
        when().request("GET", RestAssured.baseURI + port + "/api/orders/11")
                .then().statusCode(200)
                .body(containsString("\"productCount\":100"));
    }

    @Test
    void getOrdersByCustomerId() {
        when().request("GET", RestAssured.baseURI + port + "/api/orders?customerId=11")
                .then().statusCode(200);
        when().request("GET", RestAssured.baseURI + port + "/api/orders?customerId=11")
                .then().statusCode(200);

    }

    @AfterEach
    void cleanUp(){
        orderRepository.deleteAll();
    }
}