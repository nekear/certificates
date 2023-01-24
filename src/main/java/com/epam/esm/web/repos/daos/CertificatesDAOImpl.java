package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.entities.enums.SortCategories;
import com.epam.esm.web.entities.enums.SortTypes;
import com.epam.esm.web.exceptions.DBException;
import com.epam.esm.web.repos.daos.prototypes.CertificatesDAO;
import com.epam.esm.web.repos.daos.prototypes.TagsDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import com.epam.esm.web.repos.mappers.ExtendedCertificateMapper;
import com.epam.esm.web.repos.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
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
    public List<Certificate> getAll(Map<String, String> searching, LinkedHashMap<SortCategories, SortTypes> ordering) {
        String sqlQuery = "SELECT *, glueTags(gift_certificate.id) AS tags FROM gift_certificate ";

        List<Object> queryParams = new LinkedList<>();

        if(searching != null && !searching.isEmpty()){
            sqlQuery += "WHERE ";

            StringJoiner joiner = new StringJoiner(" AND ");
            if(searching.containsKey("gc")) {
                joiner.add("gift_certificate.name LIKE ? OR gift_certificate.description LIKE ?");
                queryParams.add(searching.get("gc"));
                queryParams.add(searching.get("gc"));
            }

            if(searching.containsKey("tag")){
                joiner.add("countConnectedTagsWithName(gift_certificate.id, ?) > 0");
                queryParams.add(searching.get("tag"));
            }

            sqlQuery += joiner + " ";
        }

        if(ordering != null && !ordering.isEmpty()){
            StringJoiner orderingJoiner = new StringJoiner(",", "ORDER BY ", "");

            if(ordering.containsKey(SortCategories.name))
                orderingJoiner.add("gift_certificate.name " + ordering.get(SortCategories.name));

            if(ordering.containsKey(SortCategories.date))
                orderingJoiner.add("gift_certificate.create_date " + ordering.get(SortCategories.date));

            sqlQuery += orderingJoiner.toString();
        }

        return jdbcTemplate.query(sqlQuery, queryParams.toArray(), new ExtendedCertificateMapper());
    }

    @Override
    @Transactional
    public int createOne(Certificate certificate) {
        // Creating new certificate
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO gift_certificate (name, description, price, duration) VALUES (?, ?, ?, ?)", new String[] {"id"});

            int index = 1;
            stmt.setString(index++, certificate.getName());
            stmt.setString(index++, certificate.getDescription());
            stmt.setInt(index++, certificate.getPrice());
            stmt.setInt(index++, certificate.getDuration());

            return stmt;
        }, keyHolder);

        int certificateId = keyHolder.getKey().intValue();

        List<Tag> tags = certificate.getTags();

        if(tags != null){
            // Creating new tags
            tags.stream().filter(x -> x.getId() == null).forEach(tagsDAO::createOne);

            // Connecting tags to the certificate
            tags.forEach(x -> connectTag(certificateId, x.getId()));
        }

        return certificateId;
    }

    @Override
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

    @Override
    public boolean deleteOne(int id) {
        return jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", id) > 0;
    }
}
