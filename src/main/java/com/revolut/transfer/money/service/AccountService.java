package com.revolut.transfer.money.service;

import com.revolut.transfer.money.dao.AccountDao;
import com.revolut.transfer.money.dto.*;
import com.revolut.transfer.money.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService implements IAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private AccountDao accountDao = new AccountDao();

    @Override
    public AccountDTO create(MoneyDTO initialBalance) {
        LOGGER.info("Creating account.");
        Account newAccount = new Account(Factory.create(initialBalance));
        AccountDTO createdAccount = Factory.create(accountDao.save(newAccount));
        LOGGER.info("Account successfully created. Id: " + createdAccount.getId());
        return createdAccount;
    }

    @Override
    public AccountDTO get(String accountId) {
        LOGGER.info("Searching account. Id: " + accountId);
        AccountDTO accountDTO = Factory.create(accountDao.find(accountId));
        LOGGER.info("Account found. Id: " + accountDTO.getId());
        return accountDTO;
    }

    @Override
    public CloseDTO close(String accountId) {
        LOGGER.info("Closing account. Id: " + accountId);
        boolean closed = accountDao.close(accountId);
        LOGGER.info("Account closed. Id: " + accountId + " status: " + closed);
        return new CloseDTO(accountId, closed);
    }

    @Override
    public TransferDTO transfer(String accountFrom, String accountTo, MoneyDTO money) {
        LOGGER.info("Transferring money from account Id: " + accountFrom + " to account Id: " + accountTo);
        accountDao.transfer(accountFrom, accountTo, Factory.create(money));
        LOGGER.info("Account successfully transferred.");
        return new TransferDTO(accountFrom, accountTo, money);
    }
}
