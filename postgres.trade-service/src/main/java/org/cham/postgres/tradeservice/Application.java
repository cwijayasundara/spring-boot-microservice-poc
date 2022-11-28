package org.cham.postgres.tradeservice;

import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.tradeservice.Repository.OrderRepository;
import org.cham.postgres.tradeservice.domain.Order;
import org.cham.postgres.tradeservice.domain.OrderType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Application {

	private static long orderId = 0;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner orderLoader(OrderRepository orderRepository){
		return (args -> {
			// save few orders
			LinkedList<Order> buyOrders = new LinkedList<>(List.of(
					new Order(++orderId, 1, "Elon Mask", 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
					new Order(++orderId, 2, "Joe Bloggs", 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
					new Order(++orderId, 3, "Tom Johns", 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
					new Order(++orderId, 4, "Gramme King",1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
					new Order(++orderId, 5, "Jim Carry", 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
					new Order(++orderId, 11, "Bob Marley", 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)
			));
			orderRepository.saveAll(buyOrders);
			// retreave all the orders
			orderRepository.findAll().forEach(order -> log.info(order.toString()));
		});
	}
}
