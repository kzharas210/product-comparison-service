package io.relayr.product.comparison.model.dto;

import com.opencsv.bean.CsvBindByName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductInfoDto {

    @NotBlank(message = "Name is required")
    @CsvBindByName(column = "name")
    private String name;

    @NotBlank(message = "Category is required")
    @CsvBindByName(column = "category")
    private String category;

    @NotNull(message = "Price is required")
    @CsvBindByName(column = "price")
    private Double price;

    @NotBlank(message = "Provider Name is required")
    @CsvBindByName(column = "provider name")
    private String providerName;

    private Double providerRanking;

    public ProductInfoDto() {
    }

    public ProductInfoDto(String name, String category, Double price, String providerName) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.providerName = providerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Double getProviderRanking() {
        return providerRanking;
    }

    public void setProviderRanking(Double providerRanking) {
        this.providerRanking = providerRanking;
    }
}