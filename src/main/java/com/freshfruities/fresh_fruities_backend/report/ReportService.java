package com.freshfruities.fresh_fruities_backend.report;

import com.freshfruities.fresh_fruities_backend.product.ProductRepository;
import com.freshfruities.fresh_fruities_backend.customer.CustomerRepository;
import com.freshfruities.fresh_fruities_backend.supplier.SupplierRepository;
import com.freshfruities.fresh_fruities_backend.order.OrderRepository;
import com.freshfruities.fresh_fruities_backend.sale.SaleRepository;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ReportService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;
    private final SaleRepository saleRepository;

    public ReportService(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            SupplierRepository supplierRepository,
            OrderRepository orderRepository,
            SaleRepository saleRepository) {

        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
        this.saleRepository = saleRepository;
    }

    public Map<String, Object> getSummary() {

        Map<String, Object> report = new LinkedHashMap<>();

        report.put("totalProducts",
                productRepository.count());

        report.put("totalCustomers",
                customerRepository.count());

        report.put("totalSuppliers",
                supplierRepository.count());

        report.put("totalOrders",
                orderRepository.count());

        report.put("totalSales",
                saleRepository.count());

        return report;
    }
}