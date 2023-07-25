package com.versatile.service.dto;

import com.versatile.domain.enumeration.CustomerType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Customer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDTO implements Serializable {

    private Long id;

    private String fullName;

    private String companyName;

    private CustomerType customerType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            "}";
    }
}
