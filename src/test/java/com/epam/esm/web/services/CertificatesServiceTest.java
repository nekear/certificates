package com.epam.esm.web.services;

import com.epam.esm.TestsConfig;
import com.epam.esm.config.RootConfig;
import com.epam.esm.extensions.DatabaseOperationsExtension;
import com.epam.esm.utils.Generator;
import com.epam.esm.web.entities.Certificate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, DatabaseOperationsExtension.class})
@ContextConfiguration(classes = {RootConfig.class, TestsConfig.class})
@ActiveProfiles("qa")
public class CertificatesServiceTest {
    CertificatesService certificatesService;

    @Autowired
    public CertificatesServiceTest(CertificatesService certificatesService) {
        this.certificatesService = spy(certificatesService);
    }

    @Test
    public void findCreateCertificateTest(){
        Certificate certificate = Generator.genCertificate(true);
        Certificate newCertificate = certificatesService.createCertificate(certificate);

        assertNotNull(newCertificate);
        assertEquals(1, newCertificate.getId());

        assertEquals(certificate.getTags().size(), newCertificate.getTags().size());

        // verifying find method call
        verify(certificatesService).findCertificate(newCertificate.getId());
    }
}
