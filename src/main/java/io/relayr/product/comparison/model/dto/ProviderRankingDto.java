package io.relayr.product.comparison.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProviderRankingDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Ranking is required")
    private Double ranking;

    public ProviderRankingDto() {
    }

    public ProviderRankingDto(String name, Double ranking) {
        this.name = name;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }
}