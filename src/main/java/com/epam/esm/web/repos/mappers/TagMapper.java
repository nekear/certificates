package com.epam.esm.web.repos.mappers;

import com.epam.esm.web.entities.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();

        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));

        return tag;
    }
}
