package org.nhatdang2604.dtos;

public record RawOrderItemDTO(
        ProductSkuDTO sku,
        ProductPriceDTO price,
        Long quantity
) {
    //Do nothing
}
