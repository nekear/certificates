package com.epam.esm.web.repos.daos.prototypes;

import com.epam.esm.web.entities.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificatesDAO {
    /**
     * Method for finding gift certificate by its id.
     * @param id certificate id.
     * @return found certificate or empty optional, if nothing found.
     */
    Optional<Certificate> findOne(int id);

    /**
     * Method for getting all available certificates.
     * @return list of available certificates.
     */
    List<Certificate> getAll();

    /**
     * Method for creating new certificates.
     * @param certificate certificate object with needed data.
     * @return id of newly created certificate.
    */
    int createOne(Certificate certificate);

    /**
     * Method for connecting tags to the specific certificate.
     */
    void connectTag(int certificateId, int tagId);
}
