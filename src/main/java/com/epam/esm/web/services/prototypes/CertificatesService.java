package com.epam.esm.web.services.prototypes;

import com.epam.esm.web.entities.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificatesService {
    /**
     * Method for finding a certificate by its id.
     */
    Optional<Certificate> findCertificate(int certificateId);

    /**
     * Method for getting all certificates.
     * @return list of all certificates.
     */
    List<Certificate> getAllCertificates();
}
