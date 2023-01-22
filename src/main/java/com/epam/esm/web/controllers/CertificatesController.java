package com.epam.esm.web.controllers;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.exceptions.CertificateNotFoundException;
import com.epam.esm.web.services.prototypes.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CertificatesController {

    private final CertificatesService certificatesService;

    @Autowired
    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping("/{id}")
    public Certificate findCertificate(@PathVariable("id") int certificateId){
        return certificatesService
                .findCertificate(certificateId)
                .orElseThrow(() -> new CertificateNotFoundException(certificateId));
    }

}
