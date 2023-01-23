package com.epam.esm.web.exceptions;

public class OperationException extends RuntimeException{
    private long certificateId;

    public OperationException(long certificateId) {
        super(String.format("Failed executing operation on #%d", certificateId));
        this.certificateId = certificateId;
    }

    public long getCertificateId() {
        return certificateId;
    }
}
