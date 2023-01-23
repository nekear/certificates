package com.epam.esm.web.controllers;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;
import com.epam.esm.web.exceptions.CertificateNotFoundException;
import com.epam.esm.web.exceptions.OperationException;
import com.epam.esm.web.exceptions.TagNotFoundException;
import com.epam.esm.web.services.CertificatesService;
import com.epam.esm.web.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tag> getAllTags(){
        return tagsService.getAll();
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag){
        return tagsService.createTag(tag).orElseThrow(() -> new OperationException(-1));
    }

    @GetMapping("/{id}")
    public Tag findTag(@PathVariable("id") int id){
        return tagsService
                .findTag(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") int id){
        if(!tagsService.deleteTag(id))
            throw new OperationException(id);
    }
}
