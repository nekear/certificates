package com.epam.esm.web;

import com.epam.esm.web.exceptions.CertificateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RandomRoot {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> getNames(){
        return List.of("John", "Mike", "Stacy");
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void getError(){
        throw new CertificateNotFoundException(10);
    }

}
