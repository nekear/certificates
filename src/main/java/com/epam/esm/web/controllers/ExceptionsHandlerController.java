package com.epam.esm.web.controllers;

import com.epam.esm.web.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandlerController {
    @ExceptionHandler({CertificateNotFoundException.class, TagNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseException notFoundExceptions(RuntimeException e){
        return new ResponseException(e.getMessage(), 404);
    }

    @ExceptionHandler(DBException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseException dbException(DBException e){
        if(e.getCause() != null)
            e.getCause().printStackTrace();

        return new ResponseException(e.getMessage(), 500);
    }

    @ExceptionHandler(OperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseException operationE(OperationException e){
        return new ResponseException(e.getMessage(), 400);
    }
}
