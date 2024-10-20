package org.nhatdang2604.controllers;

import org.nhatdang2604.actions.PlaceOrderAction;
import org.nhatdang2604.dtos.RawOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final PlaceOrderAction placeOrderAction;

    public OrderController(PlaceOrderAction placeOrderAction) {
        this.placeOrderAction = placeOrderAction;
    }

    @PostMapping
    public ResponseEntity<?> place(@RequestBody RawOrderDTO order) {

        try {
            placeOrderAction.exec(order);
            return ResponseEntity.accepted().build();
        } catch (Exception exception)
        {
            System.err.println(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Test");
    }
}