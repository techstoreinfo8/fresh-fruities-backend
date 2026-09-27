package com.freshfruities.fresh_fruities_backend.order;

import com.freshfruities.fresh_fruities_backend.product.Product;
import com.freshfruities.fresh_fruities_backend.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository itemRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id
                        )
                );
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order checkout(CheckoutRequest request) {

        if (request.getItems() == null ||
                request.getItems().isEmpty()) {

            throw new IllegalArgumentException(
                    "Order must contain at least one item"
            );
        }

        if (request.getOrderNumber() == null ||
                request.getOrderNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Order number is required"
            );
        }

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setOrderNumber(request.getOrderNumber());

        order.setStatus(
                Order.OrderStatus.PENDING
        );

        order.setPaymentStatus(
                request.getPaymentStatus() == null
                        ? Order.PaymentStatus.PENDING
                        : request.getPaymentStatus()
        );

        BigDecimal totalAmount = BigDecimal.ZERO;

        Order savedOrder = orderRepository.save(order);

        for (CheckoutRequest.Item requestItem :
                request.getItems()) {

            if (requestItem.getProductId() == null) {
                throw new IllegalArgumentException(
                        "Product ID is required"
                );
            }

            if (requestItem.getQuantity() == null ||
                    requestItem.getQuantity() <= 0) {

                throw new IllegalArgumentException(
                        "Quantity must be greater than 0"
                );
            }

            Product product = productRepository
                    .findById(requestItem.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found: "
                                            + requestItem.getProductId()
                            )
                    );

            int availableStock =
                    product.getStockQuantity() == null
                            ? 0
                            : product.getStockQuantity();

            int requestedQuantity =
                    requestItem.getQuantity();

            if (requestedQuantity > availableStock) {

                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                                + ". Available: "
                                + availableStock
                );
            }

            BigDecimal unitPrice =
                    product.getPrice();

            BigDecimal itemTotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(
                                    requestedQuantity
                            )
                    );

            OrderItem item = new OrderItem();

            item.setOrderId(savedOrder.getId());
            item.setProductId(product.getId());
            item.setQuantity(requestedQuantity);
            item.setUnitPrice(unitPrice);
            item.setTotalPrice(itemTotal);

            itemRepository.save(item);

            int newStock =
                    availableStock - requestedQuantity;

            product.setStockQuantity(newStock);

            productRepository.save(product);

            totalAmount =
                    totalAmount.add(itemTotal);
        }

        savedOrder.setTotalAmount(totalAmount);

        return orderRepository.save(savedOrder);
    }

    public Order updateOrder(
            Long id,
            Order order
    ) {

        Order existing = getOrder(id);

        existing.setCustomerId(
                order.getCustomerId()
        );

        existing.setStatus(
                order.getStatus()
        );

        existing.setTotalAmount(
                order.getTotalAmount()
        );

        existing.setPaymentStatus(
                order.getPaymentStatus()
        );

        return orderRepository.save(existing);
    }

    public List<OrderItem> getOrderItems(
            Long orderId
    ) {

        return itemRepository.findByOrderId(orderId);
    }

    public OrderItem addOrderItem(
            OrderItem item
    ) {

        return itemRepository.save(item);
    }
}