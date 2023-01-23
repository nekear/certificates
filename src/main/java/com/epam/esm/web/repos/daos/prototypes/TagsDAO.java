package com.epam.esm.web.repos.daos.prototypes;

import com.epam.esm.web.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagsDAO {
    /**
     * Method for creating new tags.
     * @return id of the newly created tag.
     */
    int createOne(Tag tag);

    /**
     * Method for getting tag by its id.
     */
    Optional<Tag> findOne(int id);

    /**
     * Method for getting all available tags.
     */
    List<Tag> getAll();

    /**
     * Method for deleting tags by their id.
     */
    boolean deleteOne(int id);


}
