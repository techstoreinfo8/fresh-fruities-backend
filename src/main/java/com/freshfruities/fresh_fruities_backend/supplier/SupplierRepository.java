package com.freshfruities.fresh_fruities_backend.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {
}