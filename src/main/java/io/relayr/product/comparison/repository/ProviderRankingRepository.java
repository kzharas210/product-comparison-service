package io.relayr.product.comparison.repository;

import io.relayr.product.comparison.model.Provider;
import io.relayr.product.comparison.model.ProviderRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRankingRepository extends JpaRepository<ProviderRanking, Long> {

    ProviderRanking findByProvider(Provider provider);
}