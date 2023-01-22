package com.epam.esm.web.repos.daos.prototypes;

import com.epam.esm.web.entities.Tag;

public interface TagsDAO {
    /**
     * Method for creating new tags.
     * @return id of the newly created tag.
     */
    int createOne(Tag tag);
}
