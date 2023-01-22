package com.epam.esm.web.repos.daos.prototypes;

import com.epam.esm.web.entities.Certificate;

import java.util.Optional;

public interface CertificatesDAO {
    /**
     * Method for finding gift certificate by its id.
     * @param id certificate id.
     * @return found certificate or empty optional, if nothing found.
     */
    Optional<Certificate> findOne(int id);
}
