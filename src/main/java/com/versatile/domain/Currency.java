package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Float rate;

    @JsonIgnoreProperties(value = { "currency" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "currency")
    private Balance balance;

    @JsonIgnoreProperties(value = { "currency", "client" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "currency")
    private TransactionHistory transactionHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Currency name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRate() {
        return this.rate;
    }

    public Currency rate(Float rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Balance getBalance() {
        return this.balance;
    }

    public void setBalance(Balance balance) {
        if (this.balance != null) {
            this.balance.setCurrency(null);
        }
        if (balance != null) {
            balance.setCurrency(this);
        }
        this.balance = balance;
    }

    public Currency balance(Balance balance) {
        this.setBalance(balance);
        return this;
    }

    public TransactionHistory getTransactionHistory() {
        return this.transactionHistory;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        if (this.transactionHistory != null) {
            this.transactionHistory.setCurrency(null);
        }
        if (transactionHistory != null) {
            transactionHistory.setCurrency(this);
        }
        this.transactionHistory = transactionHistory;
    }

    public Currency transactionHistory(TransactionHistory transactionHistory) {
        this.setTransactionHistory(transactionHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            "}";
    }
}
