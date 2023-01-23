package com.epam.esm.web.services;

import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.repos.daos.prototypes.TagsDAO;
import com.epam.esm.web.repos.mappers.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
    private final TagsDAO tagsDAO;

    @Autowired
    public TagsService(TagsDAO tagsDAO) {
        this.tagsDAO = tagsDAO;
    }

    public Optional<Tag> findTag(int id){
        return tagsDAO.findOne(id);
    }

    public List<Tag> getAll(){
        return tagsDAO.getAll();
    }

    public boolean deleteTag(int id){
        return tagsDAO.deleteOne(id);
    }

    public Optional<Tag> createTag(Tag tag){
        int newTagId = tagsDAO.createOne(tag);

        return this.findTag(newTagId);
    }
}
