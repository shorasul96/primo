package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.PartialObtain} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartialObtainDTO implements Serializable {

    private Long id;

    private UnitDTO unit;

    private CustomerDTO customer;

    private InventoryDTO item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public InventoryDTO getItem() {
        return item;
    }

    public void setItem(InventoryDTO item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartialObtainDTO)) {
            return false;
        }

        PartialObtainDTO partialObtainDTO = (PartialObtainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partialObtainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartialObtainDTO{" +
            "id=" + getId() +
            ", unit=" + getUnit() +
            ", customer=" + getCustomer() +
            ", item=" + getItem() +
            "}";
    }
}
