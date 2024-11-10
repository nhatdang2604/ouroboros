package org.nhatdang2604.dtos;

public record ProductSkuDTO(
        Long id,
        ProductDTO product,
        String variantDetails
) {
    //Do nothing
}
