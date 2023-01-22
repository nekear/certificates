package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import com.epam.esm.web.repos.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Certificate certificate = jdbcTemplate.queryForObject(
                    "SELECT * FROM gift_certificate WHERE id = ?",
                    new Object[]{ id },
                    new CertificateMapper()
            );

            List<Tag> tags = jdbcTemplate.query(
                    "SELECT id, name FROM tag\n" +
                    "JOIN gc_tag ON gc_tag.tag_id = tag.id\n" +
                    "WHERE gc_tag.certificate_id = ?", new Object[]{id}, new TagMapper()
            );

            certificate.setTags(tags);

            return Optional.of(certificate);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Certificate> getAll() {
        Map<Integer, Certificate> certificates =
                jdbcTemplate
                        .query("SELECT * FROM gift_certificate", new CertificateMapper())
                        .stream()
                        .collect(Collectors.toMap(Certificate::getId, x -> x));

        jdbcTemplate.query(
                "SELECT gc_tag.certificate_id, id, name FROM tag\n" +
                "JOIN gc_tag ON gc_tag.tag_id = tag.id",
                (rs, i) -> {
                    Tag tag = new TagMapper().mapRow(rs, i);
                    certificates.get(rs.getInt("certificate_id")).addTag(tag);

                    return tag;
                }
        );

        return certificates.values().stream().toList();
    }
}
