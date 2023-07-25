package com.versatile.service.dto;

import com.versatile.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.TransactionHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionHistoryDTO implements Serializable {

    private Long id;

    private Float amount;

    private String description;

    private TransactionType type;

    private CurrencyDTO currency;

    private CustomerDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public CustomerDTO getClient() {
        return client;
    }

    public void setClient(CustomerDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistoryDTO)) {
            return false;
        }

        TransactionHistoryDTO transactionHistoryDTO = (TransactionHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistoryDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", currency=" + getCurrency() +
            ", client=" + getClient() +
            "}";
    }
}
