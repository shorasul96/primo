package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Currency} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrencyDTO implements Serializable {

    private Long id;

    private String name;

    private Float rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyDTO)) {
            return false;
        }

        CurrencyDTO currencyDTO = (CurrencyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currencyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            "}";
    }
}
