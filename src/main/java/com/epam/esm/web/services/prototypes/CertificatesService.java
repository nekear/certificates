package com.epam.esm.web.services.prototypes;

import com.epam.esm.web.entities.Certificate;

import java.util.Optional;

public interface CertificatesService {
    /**
     * Service Method for finding a certificate by its id.
     */
    Optional<Certificate> findCertificate(int certificateId);
}
