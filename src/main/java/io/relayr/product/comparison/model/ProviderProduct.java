package io.relayr.product.comparison.model;

import io.relayr.product.comparison.model.core.BaseEntity;
import io.relayr.product.comparison.model.dto.ProductInfoDto;

import javax.persistence.*;

@Entity
@Table(name = "provider_products")
public class ProviderProduct extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private Double price;

    public ProviderProduct() {
    }

    public ProviderProduct(Provider provider, Product product, Double price) {
        this.provider = provider;
        this.product = product;
        this.price = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductInfoDto toDto() {
        ProductInfoDto productInfoDto = new ProductInfoDto();
        productInfoDto.setName(product.getName());
        productInfoDto.setCategory(product.getCategory());
        productInfoDto.setPrice(price);
        productInfoDto.setProviderName(provider.getName());

        if (provider.getProviderRanking() != null)
            productInfoDto.setProviderRanking(provider.getProviderRanking().getRanking());

        return productInfoDto;
    }
}