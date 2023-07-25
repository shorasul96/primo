package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "booked")
    private Integer booked;

    @Column(name = "claim")
    private Integer claim;

    @JsonIgnoreProperties(value = { "stage", "marketings", "category", "inventory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Product product;

    @JsonIgnoreProperties(value = { "inventory", "partialObtain" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Unit unit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unit", "customer", "item" }, allowSetters = true)
    private Set<PartialObtain> partialObtains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInStock() {
        return this.inStock;
    }

    public Inventory inStock(Integer inStock) {
        this.setInStock(inStock);
        return this;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getBooked() {
        return this.booked;
    }

    public Inventory booked(Integer booked) {
        this.setBooked(booked);
        return this;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

    public Integer getClaim() {
        return this.claim;
    }

    public Inventory claim(Integer claim) {
        this.setClaim(claim);
        return this;
    }

    public void setClaim(Integer claim) {
        this.claim = claim;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Inventory product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Inventory unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public Set<PartialObtain> getPartialObtains() {
        return this.partialObtains;
    }

    public void setPartialObtains(Set<PartialObtain> partialObtains) {
        if (this.partialObtains != null) {
            this.partialObtains.forEach(i -> i.setItem(null));
        }
        if (partialObtains != null) {
            partialObtains.forEach(i -> i.setItem(this));
        }
        this.partialObtains = partialObtains;
    }

    public Inventory partialObtains(Set<PartialObtain> partialObtains) {
        this.setPartialObtains(partialObtains);
        return this;
    }

    public Inventory addPartialObtain(PartialObtain partialObtain) {
        this.partialObtains.add(partialObtain);
        partialObtain.setItem(this);
        return this;
    }

    public Inventory removePartialObtain(PartialObtain partialObtain) {
        this.partialObtains.remove(partialObtain);
        partialObtain.setItem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", inStock=" + getInStock() +
            ", booked=" + getBooked() +
            ", claim=" + getClaim() +
            "}";
    }
}
