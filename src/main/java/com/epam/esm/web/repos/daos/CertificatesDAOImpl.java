package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CertificatesDAOImpl implements CertificatesDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificatesDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Certificate> findOne(int id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(
                    "SELECT * FROM gift_certificate WHERE id = ?",
                    new Object[]{ id },
                    new CertificateMapper()
            ));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }
}
