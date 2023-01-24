package com.epam.esm.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2Procedures {
    public static String glueTags(Connection con, int gId) throws SQLException {
        try(
                PreparedStatement stmt = con.prepareStatement("SELECT GROUP_CONCAT(CONCAT(id, '#', name) SEPARATOR '?') FROM tag " +
                        "JOIN gc_tag ON gc_tag.tag_id = tag.id " +
                        "WHERE gc_tag.certificate_id = ?;")
        ){
            stmt.setInt(1, gId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    return rs.getString(1);
            }

            return null;
        }
    }

    public static int countConnectedTagsWithName(Connection con, int gId, String tagText) throws SQLException {
        try(
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM gc_tag " +
                        " JOIN tag ON gc_tag.tag_id = tag.id \n" +
                        " WHERE tag.NAME LIKE ? AND gc_tag.certificate_id = ?;")
        ){
            stmt.setString(1, tagText);
            stmt.setInt(2, gId);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    return rs.getInt(1);
            }

            return 0;
        }
    }
}
