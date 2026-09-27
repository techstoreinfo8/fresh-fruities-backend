package com.freshfruities.fresh_fruities_backend.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Customer getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Customer update(Long id, Customer customer) {
        Customer existing = getById(id);

        existing.setName(customer.getName());
        existing.setPhone(customer.getPhone());
        existing.setEmail(customer.getEmail());
        existing.setAddress(customer.getAddress());
        existing.setCity(customer.getCity());
        existing.setActive(customer.getActive());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}