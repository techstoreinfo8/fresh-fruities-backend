package com.freshfruities.fresh_fruities_backend.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProductId(Long productId);
}