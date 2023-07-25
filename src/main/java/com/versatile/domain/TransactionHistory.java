package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.versatile.domain.enumeration.TransactionType;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TransactionHistory.
 */
@Entity
@Table(name = "transaction_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @JsonIgnoreProperties(value = { "balance", "transactionHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "transactionHistories", "marketings", "partialObtains" }, allowSetters = true)
    private Customer client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransactionHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return this.amount;
    }

    public TransactionHistory amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public TransactionHistory description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return this.type;
    }

    public TransactionHistory type(TransactionType type) {
        this.setType(type);
        return this;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransactionHistory currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public Customer getClient() {
        return this.client;
    }

    public void setClient(Customer customer) {
        this.client = customer;
    }

    public TransactionHistory client(Customer customer) {
        this.setClient(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        return id != null && id.equals(((TransactionHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistory{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
