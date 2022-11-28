package org.cham.postgres.productservice;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.productservice.domain.Product;
import org.cham.postgres.productservice.domain.ProductType;
import org.cham.postgres.productservice.domain.Ticker;
import org.cham.postgres.productservice.repository.ProductRepository;
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
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ProductControllerTest {
	@LocalServerPort
	@Getter
	private int port;
	private static long orderId = 0;
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ProductRepository productRepository;
	@BeforeEach
	void setUp() {
		productRepository.deleteAll();
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
	}
	@Test
	void contextLoads() {
	}

	@AfterEach
	void cleanUp(){
		productRepository.deleteAll();
	}
	@Test
	void shouldTestCreateProduct() {
		Product testProduct = Product.builder()
				.id(5)
				.symbol(Ticker.APPL)
				.productType(ProductType.SWAP)
				.lotSize(10)
				.quoteCurrency(12.34)
				.tickSize(34.45f)
				.quoteCurrency(67.90)
				.settleCurrency(234.90)
				.build();
		ResponseEntity<String> responseEntity = testRestTemplate
				.postForEntity("http://localhost:" + port + "/api/products", testProduct, String.class);
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(responseEntity.getBody().contains("5"));
	}

	@Test
	void shouldTestGetProductsByProductId() throws MalformedURLException {
		String responseText = "{\"id\":1,\"symbol\":\"APPL\",\"lotSize\":10,\"tickSize\":34.45,\"quoteCurrency\":67.9," +
				"\"settleCurrency\":234.9,\"productType\":\"BOND\"}";
		URL baseUrl = new URL("http://localhost:" + port + "/api/products/1");
		ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertTrue(response.getBody().contains(responseText));
	}

	@Test
	void shouldTestGetOrdersByCustomerIdNegative() throws MalformedURLException {
		URL baseUrl = new URL("http://localhost:" + port + "/api/products/9");
		ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String responseText = response.getBody();
		assertNull(null,responseText);
	}
	@Test
	void shouldTestGetAllProducts() throws MalformedURLException{
		URL baseUrl = new URL("http://localhost:" + port + "/api/products/all");
		ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		String productText1 = "{\"id\":1,\"symbol\":\"APPL\",\"lotSize\":10,\"tickSize\":34.45,\"quoteCurrency\":67.9,\"" +
				"settleCurrency\":234.9,\"productType\":\"BOND\"}";
		assertTrue(response.getBody().contains(productText1));
		String productText2 = "{\"id\":2,\"symbol\":\"TSLA\",\"lotSize\":30,\"tickSize\":432.5,\"quoteCurrency\":67.9,\"" +
				"settleCurrency\":234.9,\"productType\":\"STOCK\"}";
		assertTrue(response.getBody().contains(productText2));
	}
}
