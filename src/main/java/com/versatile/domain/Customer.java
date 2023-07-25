package com.versatile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.versatile.domain.enumeration.CustomerType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "client" }, allowSetters = true)
    private Set<TransactionHistory> transactionHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "product" }, allowSetters = true)
    private Set<Marketing> marketings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unit", "customer", "item" }, allowSetters = true)
    private Set<PartialObtain> partialObtains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Customer fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Customer companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CustomerType getCustomerType() {
        return this.customerType;
    }

    public Customer customerType(CustomerType customerType) {
        this.setCustomerType(customerType);
        return this;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Set<TransactionHistory> getTransactionHistories() {
        return this.transactionHistories;
    }

    public void setTransactionHistories(Set<TransactionHistory> transactionHistories) {
        if (this.transactionHistories != null) {
            this.transactionHistories.forEach(i -> i.setClient(null));
        }
        if (transactionHistories != null) {
            transactionHistories.forEach(i -> i.setClient(this));
        }
        this.transactionHistories = transactionHistories;
    }

    public Customer transactionHistories(Set<TransactionHistory> transactionHistories) {
        this.setTransactionHistories(transactionHistories);
        return this;
    }

    public Customer addTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistories.add(transactionHistory);
        transactionHistory.setClient(this);
        return this;
    }

    public Customer removeTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistories.remove(transactionHistory);
        transactionHistory.setClient(null);
        return this;
    }

    public Set<Marketing> getMarketings() {
        return this.marketings;
    }

    public void setMarketings(Set<Marketing> marketings) {
        if (this.marketings != null) {
            this.marketings.forEach(i -> i.setCustomer(null));
        }
        if (marketings != null) {
            marketings.forEach(i -> i.setCustomer(this));
        }
        this.marketings = marketings;
    }

    public Customer marketings(Set<Marketing> marketings) {
        this.setMarketings(marketings);
        return this;
    }

    public Customer addMarketing(Marketing marketing) {
        this.marketings.add(marketing);
        marketing.setCustomer(this);
        return this;
    }

    public Customer removeMarketing(Marketing marketing) {
        this.marketings.remove(marketing);
        marketing.setCustomer(null);
        return this;
    }

    public Set<PartialObtain> getPartialObtains() {
        return this.partialObtains;
    }

    public void setPartialObtains(Set<PartialObtain> partialObtains) {
        if (this.partialObtains != null) {
            this.partialObtains.forEach(i -> i.setCustomer(null));
        }
        if (partialObtains != null) {
            partialObtains.forEach(i -> i.setCustomer(this));
        }
        this.partialObtains = partialObtains;
    }

    public Customer partialObtains(Set<PartialObtain> partialObtains) {
        this.setPartialObtains(partialObtains);
        return this;
    }

    public Customer addPartialObtain(PartialObtain partialObtain) {
        this.partialObtains.add(partialObtain);
        partialObtain.setCustomer(this);
        return this;
    }

    public Customer removePartialObtain(PartialObtain partialObtain) {
        this.partialObtains.remove(partialObtain);
        partialObtain.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            "}";
    }
}
