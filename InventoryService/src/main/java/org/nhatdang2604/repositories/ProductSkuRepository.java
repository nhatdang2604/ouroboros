package org.nhatdang2604.repositories;

import jakarta.persistence.LockModeType;
import org.nhatdang2604.entities.ProductReservation;
import org.nhatdang2604.entities.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sku.id, sku FROM ProductSku sku WHERE sku.id IN :ids")
    Map<Long, ProductSku> findAndWriteLockByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query(value = """
        INSERT INTO product_skus (id, available_quantity) 
        VALUES (:skus) 
        ON DUPLICATE KEY UPDATE available_quantity = VALUES(available_quantity)
        """, nativeQuery = true)
    void batchQuantityUpdate(List<ProductSku> skus);
}
