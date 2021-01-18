package io.relayr.product.comparison.repository;

import io.relayr.product.comparison.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Provider findByName(String name);
}