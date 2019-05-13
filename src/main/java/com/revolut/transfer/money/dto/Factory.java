package com.revolut.transfer.money.dto;

import com.revolut.transfer.money.model.Account;
import com.revolut.transfer.money.model.Money;

public class Factory {

    public static AccountDTO create(Account account) {
        return new AccountDTO(account.getId(), create(account.getBalance()));
    }

    private static MoneyDTO create(Money money) {
        return new MoneyDTO(money.getAmount(), money.getCurrency());
    }

    public static Account create(AccountDTO account) {
        return new Account(create(account.getBalance()));
    }

    public static Money create(MoneyDTO money) {
        return new Money(money.getAmount(), money.getCurrency());
    }
}
