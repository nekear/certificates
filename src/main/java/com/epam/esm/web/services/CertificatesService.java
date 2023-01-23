package com.epam.esm.web.services;

import com.epam.esm.utils.Utils;
import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.entities.enums.SortCategories;
import com.epam.esm.web.entities.enums.SortTypes;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<Certificate> getAllCertificates(Optional<String> tag, Optional<String> gc, List<SortCategories> orderBy, List<SortTypes> orderTypes) {
        Map<String, String> searching = new HashMap<>();
        tag.ifPresent(x -> searching.put("tag", "%"+Utils.clean(x)+"%"));
        gc.ifPresent(x -> searching.put("gc", "%"+Utils.clean(x)+"%"));

        LinkedHashMap<SortCategories, SortTypes> ordering = new LinkedHashMap<>();

        if(orderBy != null && orderTypes != null){
            for(int i = 0; i < orderBy.size(); i++){
                if(orderTypes.get(i) != null)
                    ordering.put(orderBy.get(i), orderTypes.get(i));
            }
        }

        return certificatesDAO.getAll(searching, ordering);
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
