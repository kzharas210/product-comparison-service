package io.relayr.product.comparison.repository;

import io.relayr.product.comparison.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByNameAndCategory(String name, String category);
}