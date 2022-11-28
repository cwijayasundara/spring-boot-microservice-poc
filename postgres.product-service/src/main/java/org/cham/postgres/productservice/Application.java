package org.cham.postgres.productservice;

import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.productservice.domain.Product;
import org.cham.postgres.productservice.domain.ProductType;
import org.cham.postgres.productservice.domain.Ticker;
import org.cham.postgres.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner productLoader(ProductRepository productRepository) {
		return (args -> {
			// save few orders
			LinkedList<Product> productList = new LinkedList<>(List.of(
					Product.builder()
							.id(1)
							.symbol(Ticker.APPL)
							.productType(ProductType.BOND)
							.lotSize(10)
							.quoteCurrency(12.34)
							.tickSize(34.45f)
							.quoteCurrency(67.90)
							.settleCurrency(234.90)
							.build(),
					Product.builder()
							.id(2)
							.symbol(Ticker.TSLA)
							.productType(ProductType.STOCK)
							.lotSize(30)
							.quoteCurrency(567.45)
							.tickSize(432.50f)
							.quoteCurrency(67.90)
							.settleCurrency(234.90)
							.build()
			));
			productRepository.saveAll(productList);
			// retreave all the orders
			productRepository.findAll().forEach(order -> log.info(order.toString()));
		});
	}
}
