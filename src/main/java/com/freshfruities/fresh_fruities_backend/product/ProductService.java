package com.freshfruities.fresh_fruities_backend.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existing = getById(id);

        existing.setName(product.getName());
        existing.setCategoryId(product.getCategoryId());
        existing.setUnit(product.getUnit());
        existing.setPrice(product.getPrice());
        existing.setPurchasePrice(product.getPurchasePrice());
        existing.setStockQuantity(product.getStockQuantity());
        existing.setMinimumStock(product.getMinimumStock());
        existing.setSupplierId(product.getSupplierId());
        existing.setActive(product.getActive());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}