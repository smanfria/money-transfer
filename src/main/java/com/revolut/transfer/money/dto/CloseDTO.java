package com.revolut.transfer.money.dto;

public class CloseDTO {

    private final String id;
    private final boolean closed;


    public CloseDTO(String id, boolean closed) {
        this.id = id;
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return "CloseDTO{" +
                "id='" + id + '\'' +
                ", closed=" + closed +
                '}';
    }
}
