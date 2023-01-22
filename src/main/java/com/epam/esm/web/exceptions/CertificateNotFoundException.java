package com.epam.esm.web.exceptions;

public class CertificateNotFoundException extends RuntimeException{
    private long certificateId;

    public CertificateNotFoundException(long certificateId) {
        super(String.format("Certificate #%d not found!", certificateId));
        this.certificateId = certificateId;
    }

    public long getCertificateId() {
        return certificateId;
    }
}
