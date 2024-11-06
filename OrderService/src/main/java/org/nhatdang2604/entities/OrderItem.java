package org.nhatdang2604.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nhatdang2604.dtos.RawOrderItemDTO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long skuId;
    private Long priceId;
    private Long quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderItem parse(RawOrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(orderItemDTO.sku().id());
        orderItem.setPriceId(orderItemDTO.price().id());
        orderItem.setQuantity(orderItemDTO.quantity());

        return orderItem;
    }
}
