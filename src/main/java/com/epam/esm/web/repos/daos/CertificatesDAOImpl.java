package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.exceptions.DBException;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import com.epam.esm.web.repos.daos.prototypes.TagsDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import com.epam.esm.web.repos.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CertificatesDAOImpl implements CertificatesDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagsDAO tagsDAO;

    @Autowired
    public CertificatesDAOImpl(JdbcTemplate jdbcTemplate, TagsDAO tagsDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagsDAO = tagsDAO;
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

    @Override
    @Transactional
    public int createOne(Certificate certificate) {
        // Creating new certificate
        SimpleJdbcInsert certificateInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificate").usingGeneratedKeyColumns("id", "create_date", "last_update_date");

        HashMap<String, Object> certificateParams = new HashMap<>();
        certificateParams.put("name", certificate.getName());
        certificateParams.put("description", certificate.getDescription());
        certificateParams.put("price", certificate.getPrice());
        certificateParams.put("duration", certificate.getDuration());

        int certificateId = certificateInsert.executeAndReturnKey(certificateParams).intValue();

        List<Tag> tags = certificate.getTags();

        // Creating new tags
        tags.stream().filter(x -> x.getId() == null).forEach(tagsDAO::createOne);

        // Connecting tags to the certificate
        tags.forEach(x -> connectTag(certificateId, x.getId()));

        return certificateId;
    }

    @Override
    @Transactional
    public void connectTag(int certificateId, int tagId) {
        jdbcTemplate.update("INSERT INTO gc_tag (certificate_id, tag_id) VALUES (?, ?)", certificateId, tagId);
    }

    @Override
    @Transactional
    public void updateOne(int id, Map<String, Object> fieldsToUpdate, List<Tag> currentTags) {
        try{
            // Updating certificate information (if any present)
            if(!fieldsToUpdate.isEmpty()){
                StringJoiner updateJoiner = new StringJoiner(", ");
                fieldsToUpdate.forEach((x, y) -> updateJoiner.add(x + " = ?"));


                fieldsToUpdate.put("id", id);

                jdbcTemplate.update("UPDATE gift_certificate SET " + updateJoiner + " WHERE id = ?", fieldsToUpdate.values().toArray());
            }

            // Updating tags (if array is empty = remove all connected tags)
            if(currentTags != null){
                // Creating new tags
                currentTags.stream().filter(x -> x.getId() == null).forEach(tagsDAO::createOne);

                // Deleting connected tags
                jdbcTemplate.update("DELETE FROM gc_tag WHERE certificate_id = ?", id);

                // Inserting new connections
                currentTags.forEach(x -> connectTag(id, x.getId()));
            }
        }catch (DataIntegrityViolationException e){
            throw new DBException("Unable to execute certificate update. Maybe you specified the wrong certificate ID?", e);
        }
    }
}
