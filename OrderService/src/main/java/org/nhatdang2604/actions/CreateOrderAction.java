package org.nhatdang2604.actions;

import jakarta.transaction.Transactional;
import org.nhatdang2604.entities.Order;
import org.nhatdang2604.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderAction {

    private final OrderRepository orderRepository;

    public CreateOrderAction(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order exec(Order order)
    {
        return orderRepository.save(order);
    }
}
