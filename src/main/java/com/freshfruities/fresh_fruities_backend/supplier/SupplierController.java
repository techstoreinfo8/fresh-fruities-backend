package com.freshfruities.fresh_fruities_backend.supplier;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Supplier create(@RequestBody Supplier supplier) {
        return service.create(supplier);
    }

    @PutMapping("/{id}")
    public Supplier update(
            @PathVariable Long id,
            @RequestBody Supplier supplier) {

        return service.update(id, supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}