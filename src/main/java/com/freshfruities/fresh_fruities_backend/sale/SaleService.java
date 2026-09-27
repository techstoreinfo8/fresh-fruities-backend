package com.freshfruities.fresh_fruities_backend.sale;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository repository;

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public List<Sale> getAll() {
        return repository.findAll();
    }

    public Sale getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found"));
    }

    public Sale create(Sale sale) {
        return repository.save(sale);
    }

    public Sale update(Long id, Sale sale) {
        Sale existing = getById(id);

        existing.setOrderId(sale.getOrderId());
        existing.setCustomerId(sale.getCustomerId());
        existing.setSubtotal(sale.getSubtotal());
        existing.setTaxAmount(sale.getTaxAmount());
        existing.setDiscountAmount(sale.getDiscountAmount());
        existing.setTotalAmount(sale.getTotalAmount());
        existing.setPaymentMethod(sale.getPaymentMethod());
        existing.setPaymentStatus(sale.getPaymentStatus());

        return repository.save(existing);
    }
}