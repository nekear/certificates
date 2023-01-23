package com.epam.esm.web.controllers;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.enums.SortCategories;
import com.epam.esm.web.entities.enums.SortTypes;
import com.epam.esm.web.exceptions.CertificateNotFoundException;
import com.epam.esm.web.exceptions.OperationException;
import com.epam.esm.web.services.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @Autowired
    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<Certificate> getAllCertificates(
            @RequestParam(value = "tag") Optional<String> tagSearch,
            @RequestParam(value = "gc") Optional<String> certificateSearch,
            @RequestParam(value = "orderBy", required = false) List<SortCategories> orderBy,
            @RequestParam(value = "orderTypes", required = false) List<SortTypes> orderTypes
    ){
        return certificatesService.getAllCertificates(tagSearch, certificateSearch, orderBy, orderTypes);
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate){
        return certificatesService.createCertificate(certificate);
    }

    @GetMapping("/{id}")
    public Certificate findCertificate(@PathVariable("id") int certificateId){
        return certificatesService
                .findCertificate(certificateId)
                .orElseThrow(() -> new CertificateNotFoundException(certificateId));
    }

    @PutMapping("/{id}")
    public Certificate updateCertificate(
            @PathVariable("id") int certificateId,
            @RequestBody Certificate certificate
    ){
        return certificatesService
                .updateCertificate(certificateId, certificate)
                .orElseThrow(() -> new CertificateNotFoundException(certificateId));
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable("id") int certificateId){
        if(!certificatesService.deleteCertificate(certificateId))
            throw new OperationException(certificateId);
    }

}
