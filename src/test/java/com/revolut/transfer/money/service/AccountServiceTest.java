package com.revolut.transfer.money.service;

import com.revolut.transfer.money.dto.AccountDTO;
import com.revolut.transfer.money.dto.CloseDTO;
import com.revolut.transfer.money.dto.MoneyDTO;
import com.revolut.transfer.money.exception.AccountNotFoundException;
import com.revolut.transfer.money.exception.IncompatibleCurrencyCode;
import com.revolut.transfer.money.exception.InsufficientMoneyInAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceTest {
    private static final String USD_CURRENCY = "USD";
    private final AccountService accountService = new AccountService();
    private AccountDTO createdAccount;

    @BeforeEach
    void setUp() {
        MoneyDTO initialBalance = new MoneyDTO(1000, USD_CURRENCY);
        createdAccount = accountService.create(initialBalance);
    }

    @AfterEach
    void tearDown() {
        accountService.close(createdAccount.getId());
    }

    @Test
    void getAccount() {
        AccountDTO account = accountService.get(createdAccount.getId());
        Assertions.assertEquals(1000, account.getBalance().getAmount());
    }

    @Test
    void account_not_found() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.get("2"));
    }

    @Test
    void close() {
        MoneyDTO money = new MoneyDTO(2000, USD_CURRENCY);
        AccountDTO account = accountService.create(money);
        CloseDTO close = accountService.close(account.getId());
        Assertions.assertTrue(close.isClosed());
    }

    @Test
    void close_account_not_found() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.close("123"));
    }

    @Test
    void transfer() {
        MoneyDTO money = new MoneyDTO(2000, USD_CURRENCY);
        AccountDTO account = accountService.create(money);

        accountService.transfer(account.getId(), createdAccount.getId(), money);

        AccountDTO updatedAccountTo = accountService.get(createdAccount.getId());
        AccountDTO updatedAccountFrom = accountService.get(account.getId());

        Assertions.assertEquals(0, updatedAccountFrom.getBalance().getAmount());
        Assertions.assertEquals(3000, updatedAccountTo.getBalance().getAmount());
    }

    @Test
    void transfer_not_enough_money() {
        MoneyDTO money = new MoneyDTO(2000, USD_CURRENCY);
        AccountDTO account = accountService.create(money);

        MoneyDTO moneyToTransfer = new MoneyDTO(5000, USD_CURRENCY);
        Assertions.assertThrows(InsufficientMoneyInAccount.class, () ->
                accountService.transfer(account.getId(), createdAccount.getId(), moneyToTransfer));


    }

    @Test
    void transfer_account_not_found() {
        MoneyDTO money = new MoneyDTO(2000, USD_CURRENCY);
        Assertions.assertThrows(AccountNotFoundException.class, () ->
                accountService.transfer("1234", createdAccount.getId(), money));
    }

    @Test
    void transfer_incompatible_currency() {
        MoneyDTO money = new MoneyDTO(2000, "ARS");
        AccountDTO account = accountService.create(money);

        Assertions.assertThrows(IncompatibleCurrencyCode.class, () ->
                accountService.transfer(account.getId(), createdAccount.getId(), money));
    }

}