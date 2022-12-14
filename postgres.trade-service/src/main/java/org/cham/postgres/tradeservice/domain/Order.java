package org.cham.postgres.tradeservice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "orders")
public class Order {

    @Id
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "product_count", nullable = false)
    private int productCount;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "type", nullable = false)
    private OrderType type;

    @Column(name = "amount", nullable = false)
    private int amount;
}
