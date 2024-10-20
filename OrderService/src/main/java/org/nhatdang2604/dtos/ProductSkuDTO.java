package org.nhatdang2604.dtos;

public record ProductSkuDTO(
        Long id,
        ProductDTO product,
        ProductPriceDTO price,
        String variantDetails
) {
}
