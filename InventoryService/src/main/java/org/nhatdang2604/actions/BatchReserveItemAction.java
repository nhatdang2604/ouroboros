package org.nhatdang2604.actions;

import org.nhatdang2604.dtos.ProductSkuDTO;
import org.nhatdang2604.dtos.RawOrderDTO;
import org.nhatdang2604.dtos.RawOrderItemDTO;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.repositories.ProductSkuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BatchReserveItemAction {

    private final ProductSkuRepository productSkuRepository;

    public BatchReserveItemAction(ProductSkuRepository productSkuRepository) {
        this.productSkuRepository = productSkuRepository;
    }

    public void execute(List<PlaceOrderEvent> batchEvents) {
        Set<Long> skuIds = parseToSkuIds(batchEvents);


    }

    private Set<Long> parseToSkuIds(List<PlaceOrderEvent> batchEvents) {
        List<RawOrderDTO> orders = batchEvents
                .stream()
                .map(PlaceOrderEvent::order)
                .toList();

        List<RawOrderItemDTO> orderItems = orders
                .stream()
                .map(RawOrderDTO::items)
                .flatMap(List::stream)
                .toList();

        List<ProductSkuDTO> skus = orderItems
                .stream()
                .map(RawOrderItemDTO::sku)
                .toList();

        return skus
                .stream()
                .map((ProductSkuDTO::id))
                .collect(Collectors.toSet());
    }
}
