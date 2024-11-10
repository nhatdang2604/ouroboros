package org.nhatdang2604.dtos;

import java.math.BigDecimal;

public record ProductPriceDTO(
        Long id,
        BigDecimal price
) {
    //Do nothing
}
