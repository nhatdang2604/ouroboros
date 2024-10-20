package org.nhatdang2604.dtos;

public record RawOrderItemDTO(
        ProductSkuDTO sku,
        Integer quantity
) {
}
