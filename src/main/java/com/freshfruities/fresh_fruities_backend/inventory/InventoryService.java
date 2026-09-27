package com.freshfruities.fresh_fruities_backend.inventory;

import com.freshfruities.fresh_fruities_backend.product.Product;
import com.freshfruities.fresh_fruities_backend.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    @Transactional
    public Inventory stockIn(
            Long productId,
            Integer quantity,
            String remarks
    ) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Stock In quantity must be greater than 0"
            );
        }

        Product product = getProduct(productId);

        int previousQuantity = safeStock(product);
        int newQuantity = previousQuantity + quantity;

        product.setStockQuantity(newQuantity);
        productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setProductId(productId);
        inventory.setTransactionType(
                Inventory.TransactionType.STOCK_IN
        );
        inventory.setQuantity(quantity);
        inventory.setPreviousQuantity(previousQuantity);
        inventory.setNewQuantity(newQuantity);
        inventory.setRemarks(remarks);

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory stockOut(
            Long productId,
            Integer quantity,
            String remarks
    ) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Stock Out quantity must be greater than 0"
            );
        }

        Product product = getProduct(productId);

        int previousQuantity = safeStock(product);

        if (quantity > previousQuantity) {
            throw new IllegalArgumentException(
                    "Stock Out quantity cannot be greater than available stock"
            );
        }

        int newQuantity = previousQuantity - quantity;

        product.setStockQuantity(newQuantity);
        productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setProductId(productId);
        inventory.setTransactionType(
                Inventory.TransactionType.STOCK_OUT
        );
        inventory.setQuantity(quantity);
        inventory.setPreviousQuantity(previousQuantity);
        inventory.setNewQuantity(newQuantity);
        inventory.setRemarks(remarks);

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory adjustment(
            Long productId,
            Integer newQuantity,
            String remarks
    ) {
        if (newQuantity == null || newQuantity < 0) {
            throw new IllegalArgumentException(
                    "Adjusted stock cannot be negative"
            );
        }

        Product product = getProduct(productId);

        int previousQuantity = safeStock(product);

        product.setStockQuantity(newQuantity);
        productRepository.save(product);

        Inventory inventory = new Inventory();

        inventory.setProductId(productId);
        inventory.setTransactionType(
                Inventory.TransactionType.ADJUSTMENT
        );

        inventory.setQuantity(
                newQuantity - previousQuantity
        );

        inventory.setPreviousQuantity(previousQuantity);
        inventory.setNewQuantity(newQuantity);
        inventory.setRemarks(remarks);

        return inventoryRepository.save(inventory);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found: " + productId
                        )
                );
    }

    private int safeStock(Product product) {
        return product.getStockQuantity() == null
                ? 0
                : product.getStockQuantity();
    }
}