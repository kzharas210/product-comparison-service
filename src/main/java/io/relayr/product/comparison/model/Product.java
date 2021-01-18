package io.relayr.product.comparison.model;

import io.relayr.product.comparison.model.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column
    private String name;

    @Column
    private String category;

    public Product() {
    }

    public Product(String name, String category) {
        this.name = name;
        this.category = category;
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
}