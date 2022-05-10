package com.product.service.app.repository;

import com.product.service.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

    public Product findByName(String name);
}
