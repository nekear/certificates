package com.epam.esm.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseManager {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setup(){
        jdbcTemplate.execute("create table gift_certificate\n" +
                "(\n" +
                "    id               int auto_increment\n" +
                "        primary key,\n" +
                "    name             tinytext                            null,\n" +
                "    description      text                                null,\n" +
                "    price            int                                 null,\n" +
                "    duration         int                                 null,\n" +
                "    create_date      timestamp default CURRENT_TIMESTAMP,\n" +
                "    last_update_date timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP\n" +
                ");");

        jdbcTemplate.execute("create table tag\n" +
                "(\n" +
                "    id   int auto_increment\n" +
                "        primary key,\n" +
                "    name tinytext null\n" +
                ");");

        jdbcTemplate.execute("create table gc_tag\n" +
                "(\n" +
                "    certificate_id int null,\n" +
                "    tag_id         int null,\n" +
                "    constraint FK_gc_tag_gift_certificate\n" +
                "        foreign key (certificate_id) references gift_certificate (id)\n" +
                "            on delete cascade,\n" +
                "    constraint FK_gc_tag_tag\n" +
                "        foreign key (tag_id) references tag (id)\n" +
                "            on delete cascade\n" +
                ");");

        jdbcTemplate.execute("CREATE ALIAS glueTags for \" com.epam.esm.utils.H2Procedures.glueTags\"");
        jdbcTemplate.execute("CREATE ALIAS countConnectedTagsWithName for \" com.epam.esm.utils.H2Procedures.countConnectedTagsWithName\"");
    }

    public void destroy(){
        jdbcTemplate.execute("DROP TABLE IF EXISTS gc_tag;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS gift_certificate;");
        jdbcTemplate.execute("DROP TABLE IF EXISTS tag;");

        jdbcTemplate.execute("DROP ALIAS IF EXISTS glueTags");
        jdbcTemplate.execute("DROP ALIAS IF EXISTS countConnectedTagsWithName");
    }

}
