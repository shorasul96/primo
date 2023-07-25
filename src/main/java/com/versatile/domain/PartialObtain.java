package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartialObtain.
 */
@Entity
@Table(name = "partial_obtain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartialObtain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "inventory", "partialObtain" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "transactionHistories", "marketings", "partialObtains" }, allowSetters = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "product", "unit", "partialObtains" }, allowSetters = true)
    private Inventory item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartialObtain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public PartialObtain unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PartialObtain customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Inventory getItem() {
        return this.item;
    }

    public void setItem(Inventory inventory) {
        this.item = inventory;
    }

    public PartialObtain item(Inventory inventory) {
        this.setItem(inventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartialObtain)) {
            return false;
        }
        return id != null && id.equals(((PartialObtain) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartialObtain{" +
            "id=" + getId() +
            "}";
    }
}
