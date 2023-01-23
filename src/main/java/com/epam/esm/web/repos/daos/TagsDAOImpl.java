package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.repos.daos.prototypes.TagsDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import com.epam.esm.web.repos.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagsDAOImpl implements TagsDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagsDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int createOne(Tag tag) {
        SimpleJdbcInsert tagInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tag").usingGeneratedKeyColumns("id");

        tag.setId(tagInsert.executeAndReturnKey(Map.of("name", tag.getName())).intValue());

        return tag.getId();
    }

    @Override
    public Optional<Tag> findOne(int id) {
        try{
            Tag tag = jdbcTemplate.queryForObject(
                    "SELECT * FROM tag WHERE id = ?",
                    new Object[]{ id },
                    new TagMapper()
            );

            return Optional.of(tag);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query("SELECT * FROM tag", new TagMapper());
    }

    @Override
    public boolean deleteOne(int id) {
        return jdbcTemplate.update("DELETE FROM tag WHERE id = ?", id) > 0;
    }
}
