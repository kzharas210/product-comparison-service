package io.relayr.product.comparison.model;

import io.relayr.product.comparison.model.core.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "provider_rankings")
public class ProviderRanking extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column
    private Double ranking;

    public ProviderRanking() {
    }

    public ProviderRanking(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }
}