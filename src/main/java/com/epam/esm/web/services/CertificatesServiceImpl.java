package com.epam.esm.web.services;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import com.epam.esm.web.services.prototypes.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificatesServiceImpl implements CertificatesService {
    private final CertificatesDAO certificatesDAO;

    @Autowired
    public CertificatesServiceImpl(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    @Override
    public Optional<Certificate> findCertificate(int certificateId){
        return certificatesDAO.findOne(certificateId);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return certificatesDAO.getAll();
    }
}
