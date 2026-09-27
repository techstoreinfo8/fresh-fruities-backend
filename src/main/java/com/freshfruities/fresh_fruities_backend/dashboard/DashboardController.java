package com.freshfruities.fresh_fruities_backend.dashboard;

import com.freshfruities.fresh_fruities_backend.product.Product;
import com.freshfruities.fresh_fruities_backend.product.ProductRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ProductRepository productRepository;

    public DashboardController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboard() {

        List<Product> products = productRepository.findAll();

        long totalProducts = products.size();

        long stockAvailable = products.stream()
                .mapToLong(product ->
                        product.getStockQuantity() == null
                                ? 0
                                : product.getStockQuantity()
                )
                .sum();

        long lowStockProducts =
                products.stream()
                        .filter(product ->
                                product.getStockQuantity() != null
                                        && product.getMinimumStock() != null
                                        && product.getStockQuantity()
                                        <= product.getMinimumStock()
                        )
                        .count();

        Map<String, Object> dashboard = new LinkedHashMap<>();

        dashboard.put("totalProducts", totalProducts);
        dashboard.put("stockAvailable", stockAvailable);
        dashboard.put("lowStockProducts", lowStockProducts);

        // Future modules
        dashboard.put("todaysOrders", 0);
        dashboard.put("sales", 0);
        dashboard.put("outstandingPayments", 0);

        return dashboard;
    }
}