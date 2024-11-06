package org.nhatdang2604.repositories;

import org.nhatdang2604.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    //Do nothing
}
