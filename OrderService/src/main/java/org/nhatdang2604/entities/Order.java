package org.nhatdang2604.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nhatdang2604.dtos.RawOrderDTO;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {

    public enum Status {
        PENDING,
    }

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public static Order parse(RawOrderDTO orderDTO) {
        Order order = new Order();
        List<OrderItem> orderItems = orderDTO
                .items()
                .stream()
                .map(OrderItem::parse)
                .toList();

        order.setOrderItems(orderItems);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        return order;
    }
}
