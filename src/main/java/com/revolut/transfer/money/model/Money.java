package com.revolut.transfer.money.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Money {
    private int amount;
    private String currency;

    public Money(int amount, String currency) {

        this.amount = amount;
        this.currency = currency;
    }

    public Money() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getCurrency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return getAmount() == money.getAmount() &&
                getCurrency().equals(money.getCurrency());
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

    Money subtract(Money money) {
        return new Money(this.amount - money.getAmount(), this.currency);
    }

    Money add(Money money) {
        return new Money(this.amount + money.getAmount(), this.currency);
    }
}
