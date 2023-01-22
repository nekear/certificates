package com.epam.esm.web.entities;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class Certificate {
    private int id, price, duration;
    private String name, description;
    private ZonedDateTime createDate, lastUpdateDate;

    private List<Tag> tags = new LinkedList<>();

    public Certificate(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", price=" + price +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
