package org.nhatdang2604.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "product_reservations")
public class ProductReservation {

    public static final long STATE_SUCCESS_RESERVATION = 1;
    public static final long STATE_NEGATIVE_RESERVATION = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String transId;

    @Column
    private Long skuId;

    @Column
    private Long quantity;

    @Column
    private Long state;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
