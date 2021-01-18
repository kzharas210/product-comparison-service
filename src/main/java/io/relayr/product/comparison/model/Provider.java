package io.relayr.product.comparison.model;

import io.relayr.product.comparison.model.core.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "providers")
public class Provider extends BaseEntity {

    @Column
    private String name;

    @OneToOne(mappedBy = "provider", fetch = FetchType.LAZY)
    private ProviderRanking providerRanking;

    public Provider() {
    }

    public Provider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProviderRanking getProviderRanking() {
        return providerRanking;
    }

    public void setProviderRanking(ProviderRanking providerRanking) {
        this.providerRanking = providerRanking;
    }
}