package org.nhatdang2604.dtos;

import java.util.List;

public record RawOrderDTO(
        List<RawOrderItemDTO> items
) {
}
