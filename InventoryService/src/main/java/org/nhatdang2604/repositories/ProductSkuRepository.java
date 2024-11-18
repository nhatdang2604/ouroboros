package org.nhatdang2604.repositories;

import org.nhatdang2604.entities.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

    @Query("SELECT sku.id, sku FROM ProductSku sku WHERE sku.id IN :ids")
    public Map<Long, ProductSku> findByIds(@Param("ids") List<Long> ids);


}
