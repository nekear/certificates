package com.epam.esm.web.repos.mappers;

import com.epam.esm.web.entities.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.LinkedList;


public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();

        certificate.setId(rs.getInt("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getInt("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toInstant().atZone(ZoneOffset.UTC));
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toInstant().atZone(ZoneOffset.UTC));
        certificate.setTags(new LinkedList<>());

        return certificate;
    }
}
