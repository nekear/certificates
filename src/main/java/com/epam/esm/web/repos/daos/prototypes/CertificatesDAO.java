package com.epam.esm.web.repos.daos.prototypes;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.entities.enums.SortCategories;
import com.epam.esm.web.entities.enums.SortTypes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    List<Certificate> getAll(Map<String, String> searching, LinkedHashMap<SortCategories, SortTypes> ordering);

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

    /**
     * Method for updating specific certificate data.
     * @param id certificate id
     * @param fieldsToUpdate pairs of keys (column name) and values (new value)
     * @param currentTags certificate tags, which were passed for update or null if no update needed
     * @return true or false, depending on successfulness of operation.
     */
    void updateOne(int id, Map<String, Object> fieldsToUpdate, List<Tag> currentTags);

    /**
     * Method for deleting certificates by their id.
     */
    boolean deleteOne(int id);
}
