package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Balance.
 */
@Entity
@Table(name = "balance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "loan")
    private Float loan;

    @JsonIgnoreProperties(value = { "balance", "transactionHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Balance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return this.amount;
    }

    public Balance amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getLoan() {
        return this.loan;
    }

    public Balance loan(Float loan) {
        this.setLoan(loan);
        return this;
    }

    public void setLoan(Float loan) {
        this.loan = loan;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Balance currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Balance)) {
            return false;
        }
        return id != null && id.equals(((Balance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Balance{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", loan=" + getLoan() +
            "}";
    }
}
