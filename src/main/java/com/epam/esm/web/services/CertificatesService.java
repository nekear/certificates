package com.epam.esm.web.services;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificatesService {
    private final CertificatesDAO certificatesDAO;

    @Autowired
    public CertificatesService(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    /**
     * Method for finding a certificate by its id.
     */
    public Optional<Certificate> findCertificate(int certificateId){
        return certificatesDAO.findOne(certificateId);
    }

    /**
     * Method for acquiring all available certificates.
     */
    public List<Certificate> getAllCertificates() {
        return certificatesDAO.getAll();
    }

    public Certificate createCertificate(Certificate certificate){
        int newCertificateId = certificatesDAO.createOne(certificate);

        return this.findCertificate(newCertificateId).orElse(null);
    }
}
