package com.freshfruities.fresh_fruities_backend.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getById(
            @PathVariable Long id
    ) {
        return service.getOrder(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getItems(
            @PathVariable Long id
    ) {
        return service.getOrderItems(id);
    }

    @PostMapping
    public Order create(
            @RequestBody Order order
    ) {
        return service.createOrder(order);
    }

    @PostMapping("/checkout")
    public Order checkout(
            @RequestBody CheckoutRequest request
    ) {
        return service.checkout(request);
    }

    @PostMapping("/{id}/items")
    public OrderItem addItem(
            @PathVariable Long id,
            @RequestBody OrderItem item
    ) {

        item.setOrderId(id);

        return service.addOrderItem(item);
    }

    @PutMapping("/{id}")
    public Order update(
            @PathVariable Long id,
            @RequestBody Order order
    ) {

        return service.updateOrder(
                id,
                order
        );
    }
}