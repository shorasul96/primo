package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "measurement")
    private String measurement;

    @Column(name = "description")
    private String description;

    @JsonIgnoreProperties(value = { "product", "unit", "partialObtains" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "unit")
    private Inventory inventory;

    @JsonIgnoreProperties(value = { "unit", "customer", "item" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "unit")
    private PartialObtain partialObtain;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeasurement() {
        return this.measurement;
    }

    public Unit measurement(String measurement) {
        this.setMeasurement(measurement);
        return this;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getDescription() {
        return this.description;
    }

    public Unit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        if (this.inventory != null) {
            this.inventory.setUnit(null);
        }
        if (inventory != null) {
            inventory.setUnit(this);
        }
        this.inventory = inventory;
    }

    public Unit inventory(Inventory inventory) {
        this.setInventory(inventory);
        return this;
    }

    public PartialObtain getPartialObtain() {
        return this.partialObtain;
    }

    public void setPartialObtain(PartialObtain partialObtain) {
        if (this.partialObtain != null) {
            this.partialObtain.setUnit(null);
        }
        if (partialObtain != null) {
            partialObtain.setUnit(this);
        }
        this.partialObtain = partialObtain;
    }

    public Unit partialObtain(PartialObtain partialObtain) {
        this.setPartialObtain(partialObtain);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", measurement='" + getMeasurement() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
