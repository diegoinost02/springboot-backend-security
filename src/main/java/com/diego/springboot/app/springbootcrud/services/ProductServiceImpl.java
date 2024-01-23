package com.diego.springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diego.springboot.app.springbootcrud.entities.Product;
import com.diego.springboot.app.springbootcrud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) this.repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        if (id != null) {
            return this.repository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Product save(Product product) {
        if (product == null) {
            return null;
        }
        return this.repository.save(product);
    }

     
    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productOptional = this.repository.findById(id);
        if(productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow();

            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setDescription(product.getDescription());
            productDb.setSku(product.getSku());
            return Optional.of(this.repository.save(productDb)); //save devuelve un objeto de tipo Product, por eso se usa Optional.of
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = this.repository.findById(id);
        productOptional.ifPresent(productDb -> {
            repository.delete(productDb);
        });
        return productOptional;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsBySku(String sku) {
        return this.repository.existsBySku(sku);
    }
}
