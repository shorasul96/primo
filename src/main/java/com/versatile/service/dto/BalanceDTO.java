package com.versatile.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.versatile.domain.Balance} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BalanceDTO implements Serializable {

    private Long id;

    private Float amount;

    private Float loan;

    private CurrencyDTO currency;

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

    public Float getLoan() {
        return loan;
    }

    public void setLoan(Float loan) {
        this.loan = loan;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalanceDTO)) {
            return false;
        }

        BalanceDTO balanceDTO = (BalanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, balanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BalanceDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", loan=" + getLoan() +
            ", currency=" + getCurrency() +
            "}";
    }
}
