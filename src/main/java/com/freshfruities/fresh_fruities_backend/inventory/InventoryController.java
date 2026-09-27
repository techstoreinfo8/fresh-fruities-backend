package com.freshfruities.fresh_fruities_backend.inventory;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }

    @PostMapping("/stock-in")
    public Inventory stockIn(
            @RequestBody Map<String, Object> request
    ) {
        Long productId = Long.valueOf(
                request.get("productId").toString()
        );

        Integer quantity = Integer.valueOf(
                request.get("quantity").toString()
        );

        String remarks = request.get("remarks") == null
                ? null
                : request.get("remarks").toString();

        return inventoryService.stockIn(
                productId,
                quantity,
                remarks
        );
    }

    @PostMapping("/stock-out")
    public Inventory stockOut(
            @RequestBody Map<String, Object> request
    ) {
        Long productId = Long.valueOf(
                request.get("productId").toString()
        );

        Integer quantity = Integer.valueOf(
                request.get("quantity").toString()
        );

        String remarks = request.get("remarks") == null
                ? null
                : request.get("remarks").toString();

        return inventoryService.stockOut(
                productId,
                quantity,
                remarks
        );
    }

    @PostMapping("/adjustment")
    public Inventory adjustment(
            @RequestBody Map<String, Object> request
    ) {
        Long productId = Long.valueOf(
                request.get("productId").toString()
        );

        Integer quantity = Integer.valueOf(
                request.get("quantity").toString()
        );

        String remarks = request.get("remarks") == null
                ? null
                : request.get("remarks").toString();

        return inventoryService.adjustment(
                productId,
                quantity,
                remarks
        );
    }
}