package org.cham.postgres.productservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Table(name = "products")
public class Product {

    @Id
    private int id;

    @Column(name = "symbol", nullable = false)
    private Ticker symbol;

    @Column(name = "lot_size", nullable = false)
    private int lotSize;

    @Column(name = "tick_size", nullable = false)
    private float tickSize;

    @Column(name = "quote_currency", nullable = false)
    private double quoteCurrency;

    @Column(name = "settle_currency", nullable = false)
    private double settleCurrency;

    @Column(name = "product_type", nullable = false)
    private ProductType productType;

}
