package com.versatile.service.dto;

import com.versatile.domain.enumeration.DealType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Marketing} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarketingDTO implements Serializable {

    private Long id;

    private DealType deal;

    private CustomerDTO customer;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DealType getDeal() {
        return deal;
    }

    public void setDeal(DealType deal) {
        this.deal = deal;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarketingDTO)) {
            return false;
        }

        MarketingDTO marketingDTO = (MarketingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, marketingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarketingDTO{" +
            "id=" + getId() +
            ", deal='" + getDeal() + "'" +
            ", customer=" + getCustomer() +
            ", product=" + getProduct() +
            "}";
    }
}
