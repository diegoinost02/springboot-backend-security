package com.diego.springboot.app.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.diego.springboot.app.springbootcrud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    boolean existsBySku(String sku);
}
