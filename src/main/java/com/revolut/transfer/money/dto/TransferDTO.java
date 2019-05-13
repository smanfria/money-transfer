package com.revolut.transfer.money.dto;

public class TransferDTO {
    private final String accountFrom;
    private final String accountTo;
    private final MoneyDTO money;

    public TransferDTO(String accountFrom, String accountTo, MoneyDTO money) {

        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public MoneyDTO getMoney() {
        return money;
    }
}
