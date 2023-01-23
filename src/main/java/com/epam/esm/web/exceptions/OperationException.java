package com.epam.esm.web.exceptions;

public class OperationException extends RuntimeException{
    private long unitId;

    public OperationException(long id) {
        super(String.format("Failed executing operation on #%d", id));
        this.unitId = id;
    }

    public long getUnitId() {
        return unitId;
    }
}
