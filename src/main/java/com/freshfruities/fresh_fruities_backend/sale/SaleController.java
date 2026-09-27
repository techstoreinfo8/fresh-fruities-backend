package com.freshfruities.fresh_fruities_backend.sale;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sale> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Sale getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Sale create(@RequestBody Sale sale) {
        return service.create(sale);
    }

    @PutMapping("/{id}")
    public Sale update(
            @PathVariable Long id,
            @RequestBody Sale sale) {

        return service.update(id, sale);
    }
}