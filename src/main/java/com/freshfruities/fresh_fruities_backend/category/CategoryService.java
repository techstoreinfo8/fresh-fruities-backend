package com.freshfruities.fresh_fruities_backend.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public Category update(Long id, Category category) {
        Category existing = getById(id);

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setActive(category.getActive());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}