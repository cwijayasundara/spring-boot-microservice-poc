package org.cham.postgres.productservice.repository;


import org.cham.postgres.productservice.domain.Product;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findById(int id);

    List<Product> findAll();

    <S extends Product> S save(S entity);
}
