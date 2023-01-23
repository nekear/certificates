package com.epam.esm.web.services;

import com.epam.esm.utils.Utils;
import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * Method for creating new certificate.
     * @return new certificate entity from db.
     */
    public Certificate createCertificate(Certificate certificate){
        int newCertificateId = certificatesDAO.createOne(certificate);

        return this.findCertificate(newCertificateId).orElse(null);
    }

    /**
     * Method for updating certificate data. Updates only specified fields.
     * @return updated certificate entity from db.
     */
    public Optional<Certificate> updateCertificate(int id, Certificate certificate){
        Map<String, Object> fieldsToUpdate = Utils.getUpdateDBMapping(
                certificate,
                Map.of(
                        "name", Certificate::getName,
                        "description", Certificate::getDescription,
                        "price", Certificate::getPrice,
                        "duration", Certificate::getDuration
                )
        );

        certificatesDAO.updateOne(id, fieldsToUpdate, certificate.getTags());

        return this.findCertificate(id);
    }

    /**
     * Method for deleting certificates.
     */
    public boolean deleteCertificate(int id){
        return certificatesDAO.deleteOne(id);
    }
}
