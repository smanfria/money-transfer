package com.revolut.transfer.money.model;

import com.revolut.transfer.money.exception.IncompatibleCurrencyCode;
import com.revolut.transfer.money.exception.InsufficientMoneyInAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Money balance;

    public Account(Money initialBalance) {
        this.balance = initialBalance;
    }

    public Account() {
    }

    public void withdraw(Money money) {
        this.checkSameCurrency(money);
        this.checkSufficientMoney(money);
        this.doWithDraw(money);
    }

    public void deposit(Money money) {
        this.checkSameCurrency(money);
        this.doWithDeposit(money);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    Money balance() {
        return balance;
    }

    private void doWithDeposit(Money money) {
        balance = balance.add(money);
    }

    private void doWithDraw(Money money) {
        balance = balance.subtract(money);
    }

    private void checkSufficientMoney(Money money) {
        if (this.balance().getAmount() < (money.getAmount())) {
            throw new InsufficientMoneyInAccount("Not enough money. Id= " + this.getId());
        }
    }

    private void checkSameCurrency(Money money) {
        if (!this.currency().equals(money.getCurrency())) {
            throw new IncompatibleCurrencyCode("Currency code is not the same as account currency. Id= "
                    + this.getId() + " currency: " + this.balance().getCurrency());
        }
    }

    private String currency() {
        return balance.getCurrency();
    }
}
