package org.cham.postgres.productservice.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cham.postgres.productservice.domain.Product;
import org.cham.postgres.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Setter
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/api/products/{productId}") // GET /api/products/2
    @ResponseStatus(HttpStatus.OK)
    public Optional<Product> findProductById(@PathVariable String productId){
        log.info("inside ProductController.findProductById" + productId);
        Integer id = Integer.parseInt(productId);
        return productService.getProductById(id);
    }

    @GetMapping("/api/products/all") // GET /api/products/all
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(){
        log.info("inside ProductController.getProducts");
        return productService.getAllProducts();
    }

    @PostMapping("/api/products") // POST /api/orders
    @ResponseStatus(HttpStatus.OK)
    public int createProduct(@RequestBody Product product){
        log.info("Inside ProductController.createProduct()", product.toString());
        productService.save(product);
        return product.getId();
    }
}
