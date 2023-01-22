package com.epam.esm.web.exceptions;

public class ResponseException {
    private String message;
    private int code;

    public ResponseException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
