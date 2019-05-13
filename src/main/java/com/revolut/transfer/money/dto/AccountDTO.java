package com.revolut.transfer.money.dto;

public class AccountDTO {

    private final String id;
    private final MoneyDTO balance;


    AccountDTO(String id, MoneyDTO balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public MoneyDTO getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
