package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Inventory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryDTO implements Serializable {

    private Long id;

    private Integer inStock;

    private Integer booked;

    private Integer claim;

    private ProductDTO product;

    private UnitDTO unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

    public Integer getClaim() {
        return claim;
    }

    public void setClaim(Integer claim) {
        this.claim = claim;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryDTO)) {
            return false;
        }

        InventoryDTO inventoryDTO = (InventoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + getId() +
            ", inStock=" + getInStock() +
            ", booked=" + getBooked() +
            ", claim=" + getClaim() +
            ", product=" + getProduct() +
            ", unit=" + getUnit() +
            "}";
    }
}
