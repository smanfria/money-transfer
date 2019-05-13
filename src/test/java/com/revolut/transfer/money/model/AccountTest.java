package com.revolut.transfer.money.model;

import com.revolut.transfer.money.exception.InsufficientMoneyInAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

    private static final String USD_CURRENCY_CODE = "USD";

    private Account account;
    private Money initialBalance = new Money(1000, USD_CURRENCY_CODE);

    @BeforeEach
    void setUp() {
        account = new Account( initialBalance);
    }

    @Test
    void withdraw() {
        Assertions.assertEquals(initialBalance, account.balance());
        Money money = new Money(1000, USD_CURRENCY_CODE);
        account.withdraw(money);
        Money expectedBalance = new Money(0, USD_CURRENCY_CODE);
        Assertions.assertEquals(expectedBalance, account.balance());

    }

    @Test
    void withdraw_fail() {
        Assertions.assertEquals(initialBalance, account.balance());
        Money money = new Money(10000, USD_CURRENCY_CODE);
        Assertions.assertThrows(InsufficientMoneyInAccount.class, () -> account.withdraw(money), "Insufficient funds.");
    }

    @Test
    void deposit() {
        Assertions.assertEquals(initialBalance, account.balance());
        Money money = new Money(1000, USD_CURRENCY_CODE);
        account.deposit(money);
        Money expectedBalance = new Money(2000, USD_CURRENCY_CODE);
        Assertions.assertEquals(expectedBalance, account.balance());
    }

    @Test
    void balance() {
        Assertions.assertEquals(initialBalance, account.balance());
        Money money = new Money(1000, USD_CURRENCY_CODE);
        account.deposit(money);
        Money expectedBalance = new Money(2000, USD_CURRENCY_CODE);
        Assertions.assertEquals(expectedBalance, account.balance());
        account.withdraw(money);
        Assertions.assertEquals(initialBalance, account.balance());
    }
}