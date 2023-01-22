package com.epam.esm.web.repos.daos;

import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.repos.daos.prototypes.TagsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
}
