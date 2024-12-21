package org.nhatdang2604.repositories;

import org.nhatdang2604.entities.ProductReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReservationRepository extends JpaRepository<ProductReservation, Long> {
    //do nothing
}
