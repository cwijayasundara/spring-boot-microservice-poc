package org.cham.postgres.productservice.service;

import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.productservice.domain.Product;
import org.cham.postgres.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getProductById(int orderId) {
        log.info("inside ProductService.getOrderById");
        return productRepository.findById(orderId);
    }

    public List<Product> getAllProducts(){
        log.info("inside ProductService.findAll");
        return productRepository.findAll();
    }

    public int save(Product product){
        log.info("inside ProductService.save");
        productRepository.save(product);
        return product.getId();
    }
}
