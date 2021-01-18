package io.relayr.product.comparison.repository;

import io.relayr.product.comparison.model.Product;
import io.relayr.product.comparison.model.Provider;
import io.relayr.product.comparison.model.ProviderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderProductRepository extends JpaRepository<ProviderProduct, Long> {

    ProviderProduct findByProviderAndProduct(Provider provider, Product product);

    List<ProviderProduct> findByProduct(Product product);
}