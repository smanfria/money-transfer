package com.revolut.transfer.money.service;

import com.revolut.transfer.money.dto.AccountDTO;
import com.revolut.transfer.money.dto.CloseDTO;
import com.revolut.transfer.money.dto.MoneyDTO;
import com.revolut.transfer.money.dto.TransferDTO;

public interface IAccountService {

    AccountDTO create(MoneyDTO initialBalance);

    AccountDTO get(String accountId);

    CloseDTO close(String accountId);

    TransferDTO transfer(String accountFrom, String accountTo, MoneyDTO money);
}
