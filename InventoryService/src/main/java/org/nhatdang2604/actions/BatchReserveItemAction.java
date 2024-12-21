package org.nhatdang2604.actions;

import jakarta.transaction.Transactional;
import org.nhatdang2604.dtos.ProductSkuDTO;
import org.nhatdang2604.dtos.RawOrderDTO;
import org.nhatdang2604.dtos.RawOrderItemDTO;
import org.nhatdang2604.entities.ProductReservation;
import org.nhatdang2604.entities.ProductSku;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.repositories.ProductReservationRepository;
import org.nhatdang2604.repositories.ProductSkuRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatchReserveItemAction {

    private final ProductSkuRepository productSkuRepository;
    private final ProductReservationRepository productReservationRepository;
    private Map<Long, ProductSku> skusFromDB;

    public BatchReserveItemAction(ProductSkuRepository productSkuRepository, ProductReservationRepository productReservationRepository) {
        this.productSkuRepository = productSkuRepository;
        this.productReservationRepository = productReservationRepository;
    }

    @Transactional
    public List<Map<String, List<ProductReservation>>> execute(List<PlaceOrderEvent> batchEvents) {
        List<Long> skuIds = parseToSkuIds(batchEvents);
        this.skusFromDB = this.productSkuRepository.findAndWriteLockByIds(skuIds);

        Map<String, List<ProductReservation>> reservationPool = new HashMap<>();
        Map<String, List<ProductReservation>> successReservationPool = new HashMap<>();
        Map<String, List<ProductReservation>> failedReservationPool = new HashMap<>();
        for (PlaceOrderEvent event : batchEvents) {
            List<ProductReservation> reservations = event
                    .order()
                    .items()
                    .stream()
                    .map((RawOrderItemDTO item) -> this.reserve(event.transId(), item))
                    .toList();

            boolean isSuccess = reservations.stream()
                    .allMatch((ProductReservation reservation) -> ProductReservation.STATE_SUCCESS_RESERVATION == reservation.getState());

            reservationPool.put(event.transId(), reservations);
            if (isSuccess) {
                successReservationPool.put(event.transId(), reservations);
            } else {
                failedReservationPool.put(event.transId(), reservations);
            }
        }
        this.saveToDB(reservationPool);

        return List.of(successReservationPool, failedReservationPool);
    }

    private List<Long> parseToSkuIds(List<PlaceOrderEvent> batchEvents) {
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
                .toList();
    }

    private ProductReservation reserve(String transId, RawOrderItemDTO itemFromEvent) {
        ProductSkuDTO skuFromEvent = itemFromEvent.sku();

        Long skuId = skuFromEvent.id();
        ProductSku skuFromDB = Optional
                .ofNullable(skusFromDB.get(skuId))
                .orElseThrow(() -> new IllegalArgumentException("Not found sku with id = " + skuId));

        //Calculate remain (to avoid negative repository)
        ProductReservation reservation = new ProductReservation();
        Long quantityFromEvent = itemFromEvent.quantity();
        Long quantityFromDB = skuFromDB.getAvailableQuantity();
        Long remain = quantityFromDB - quantityFromEvent;

        //Build reservation
        Long state = null;
        if (remain < 0) {
            state = ProductReservation.STATE_NEGATIVE_RESERVATION;
        } else {

            state = ProductReservation.STATE_SUCCESS_RESERVATION;

            //Decrease the quantity in DB
            skuFromDB.setAvailableQuantity(remain);
            skusFromDB.put(skuId, skuFromDB);
        }

        reservation.setSkuId(skuId);
        reservation.setQuantity(quantityFromEvent);
        reservation.setTransId(transId);
        reservation.setState(state);

        return reservation;
    }

    private void saveToDB(Map<String, List<ProductReservation>> reservationPool) {
        List<ProductReservation> toInsertReservations = reservationPool
                .values()
                .stream()
                .flatMap(List::stream)
                .toList();
        List<ProductSku> toUpdateSkus = this.skusFromDB.values().stream().toList();
        this.productReservationRepository.saveAll(toInsertReservations);
        this.productSkuRepository.batchQuantityUpdate(toUpdateSkus);
    }
}
