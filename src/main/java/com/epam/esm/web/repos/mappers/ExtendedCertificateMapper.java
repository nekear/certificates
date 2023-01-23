package com.epam.esm.web.repos.mappers;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class ExtendedCertificateMapper extends CertificateMapper {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = super.mapRow(rs, rowNum);

        if(rs.getString("tags") != null)
            certificate.setTags(
                    Arrays.stream(rs.getString("tags").split("\\?"))
                            .map(tagStr -> {
                                String[] tagData = tagStr.split("#");
                                Tag tag = new Tag();
                                tag.setId(Integer.parseInt(tagData[0]));
                                tag.setName(tagData[1]);
                                return tag;
                            }).toList()
            );

        return certificate;
    }
}
