package com.revolut.transfer.money.exception;

public class InsufficientMoneyInAccount extends RuntimeException {

    public InsufficientMoneyInAccount(String message) {
        super(message);
    }
}
