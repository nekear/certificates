package com.epam.esm.web.controllers;

import com.epam.esm.web.exceptions.CertificateNotFoundException;
import com.epam.esm.web.exceptions.DBException;
import com.epam.esm.web.exceptions.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandlerController {
    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseException certificateNFE(CertificateNotFoundException e){
        return new ResponseException(e.getMessage(), 404);
    }

    @ExceptionHandler(DBException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseException certificateNOE(DBException e){
        if(e.getCause() != null)
            e.getCause().printStackTrace();

        return new ResponseException(e.getMessage(), 500);
    }
}
