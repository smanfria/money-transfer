package com.revolut.transfer.money.exception;

public class IncompatibleCurrencyCode extends RuntimeException {
    public IncompatibleCurrencyCode(String message) {
        super(message);
    }
}
