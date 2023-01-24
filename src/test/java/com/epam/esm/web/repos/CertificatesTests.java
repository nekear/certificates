package com.epam.esm.web.repos;

import com.epam.esm.TestsConfig;
import com.epam.esm.config.RootConfig;
import com.epam.esm.extensions.DatabaseOperationsExtension;
import com.epam.esm.utils.Generator;
import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.enums.SortCategories;
import com.epam.esm.web.entities.enums.SortTypes;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, DatabaseOperationsExtension.class})
@ContextConfiguration(classes = {RootConfig.class, TestsConfig.class})
@ActiveProfiles("qa")
public class CertificatesTests {
    @Autowired
    CertificatesDAO certificatesDAO;

    @Test
    public void createOneFindOneTest(){
        Certificate certificate = Generator.genCertificate(true);

        int id = certificatesDAO.createOne(certificate);

        assertEquals(1, id);

        Certificate foundCertificate = certificatesDAO.findOne(id).orElse(null);

        assertNotNull(foundCertificate);
        // Comparing certificates' ids
        assertEquals(id, foundCertificate.getId());
        // Comparing tags amount
        assertEquals(certificate.getTags().size(), foundCertificate.getTags().size());
    }

    @Test
    public void getAllTest(){
        Certificate certificate = Generator.genCertificate(true);
        int id = certificatesDAO.createOne(certificate);

        var searchCriteria = Map.of(
                "gc", certificate.getName().substring(2, 4),
                "tag", certificate.getTags().get(0).getName().substring(0, 2)
        );

        var sortCriteria = new LinkedHashMap<>(Map.of(
                SortCategories.name, SortTypes.asc,
                SortCategories.date, SortTypes.desc
        ));

        System.out.println(certificate);
        System.out.println(searchCriteria);
        System.out.println(sortCriteria);

        List<Certificate> foundCertificates = certificatesDAO.getAll(searchCriteria, sortCriteria);

        // Comparing found amount
        assertEquals(1, foundCertificates.size());
        // Comparing found certificate id's
        assertEquals(id, foundCertificates.get(0).getId());
        // Comparing found certificate tags (checking for tags presence)
        assertEquals(certificate.getTags().size(), foundCertificates.get(0).getTags().size());
    }
}
