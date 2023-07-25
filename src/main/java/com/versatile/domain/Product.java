package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ManufactureStage stage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "product" }, allowSetters = true)
    private Set<Marketing> marketings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "categories", "products", "parent" }, allowSetters = true)
    private Category category;

    @JsonIgnoreProperties(value = { "product", "unit", "partialObtains" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    private Inventory inventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ManufactureStage getStage() {
        return this.stage;
    }

    public void setStage(ManufactureStage manufactureStage) {
        this.stage = manufactureStage;
    }

    public Product stage(ManufactureStage manufactureStage) {
        this.setStage(manufactureStage);
        return this;
    }

    public Set<Marketing> getMarketings() {
        return this.marketings;
    }

    public void setMarketings(Set<Marketing> marketings) {
        if (this.marketings != null) {
            this.marketings.forEach(i -> i.setProduct(null));
        }
        if (marketings != null) {
            marketings.forEach(i -> i.setProduct(this));
        }
        this.marketings = marketings;
    }

    public Product marketings(Set<Marketing> marketings) {
        this.setMarketings(marketings);
        return this;
    }

    public Product addMarketing(Marketing marketing) {
        this.marketings.add(marketing);
        marketing.setProduct(this);
        return this;
    }

    public Product removeMarketing(Marketing marketing) {
        this.marketings.remove(marketing);
        marketing.setProduct(null);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        if (this.inventory != null) {
            this.inventory.setProduct(null);
        }
        if (inventory != null) {
            inventory.setProduct(this);
        }
        this.inventory = inventory;
    }

    public Product inventory(Inventory inventory) {
        this.setInventory(inventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
