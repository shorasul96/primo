package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.versatile.domain.enumeration.DealType;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Marketing.
 */
@Entity
@Table(name = "marketing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Marketing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal")
    private DealType deal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "transactionHistories", "marketings", "partialObtains" }, allowSetters = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "stage", "marketings", "category", "inventory" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Marketing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DealType getDeal() {
        return this.deal;
    }

    public Marketing deal(DealType deal) {
        this.setDeal(deal);
        return this;
    }

    public void setDeal(DealType deal) {
        this.deal = deal;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Marketing customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Marketing product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marketing)) {
            return false;
        }
        return id != null && id.equals(((Marketing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marketing{" +
            "id=" + getId() +
            ", deal='" + getDeal() + "'" +
            "}";
    }
}
