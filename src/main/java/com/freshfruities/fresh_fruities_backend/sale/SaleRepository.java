package com.freshfruities.fresh_fruities_backend.sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository
        extends JpaRepository<Sale, Long> {
}