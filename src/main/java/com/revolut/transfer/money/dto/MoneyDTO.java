package com.revolut.transfer.money.dto;

public class MoneyDTO {
    private final int amount;
    private final String currency;

    public MoneyDTO(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "MoneyDTO{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
