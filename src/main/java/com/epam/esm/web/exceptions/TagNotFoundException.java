package com.epam.esm.web.exceptions;

public class TagNotFoundException extends RuntimeException{
    private long tagId;

    public TagNotFoundException(long tagId) {
        super(String.format("Tag #%d not found!", tagId));
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}
