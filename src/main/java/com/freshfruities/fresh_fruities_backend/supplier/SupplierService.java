package com.freshfruities.fresh_fruities_backend.supplier;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<Supplier> getAll() {
        return repository.findAll();
    }

    public Supplier getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found"));
    }

    public Supplier create(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier update(Long id, Supplier supplier) {
        Supplier existing = getById(id);

        existing.setName(supplier.getName());
        existing.setContactPerson(supplier.getContactPerson());
        existing.setPhone(supplier.getPhone());
        existing.setEmail(supplier.getEmail());
        existing.setAddress(supplier.getAddress());
        existing.setCity(supplier.getCity());
        existing.setActive(supplier.getActive());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}