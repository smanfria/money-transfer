package com.revolut.transfer.money.dto;

public class ErrorDTO {
    private final int statusCode;
    private final String message;

    public ErrorDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
