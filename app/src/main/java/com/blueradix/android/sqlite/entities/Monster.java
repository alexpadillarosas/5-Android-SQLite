package com.blueradix.android.sqlite.entities;

import java.io.Serializable;

public class Monster implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Integer scariness;
    private String imageFileName;
    private Integer votes;
    private Integer stars;

    public Monster() {
    }

    public Monster(Long id, String name, String description, Integer scariness, String imageFileName, Integer votes, Integer stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.scariness = scariness;
        this.imageFileName = imageFileName;
        this.votes = votes;
        this.stars = stars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getScariness() {
        return scariness;
    }

    public void setScariness(Integer scariness) {
        this.scariness = scariness;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id= " + id + System.lineSeparator() +
                "name= " + name + System.lineSeparator() +
                "description= " + description + System.lineSeparator() +
                "scariness=" + scariness + System.lineSeparator() +
                "imageFileName= " + imageFileName + System.lineSeparator() +
                "votes= " + votes + System.lineSeparator() +
                "stars= " + stars + System.lineSeparator() +
                "}" + System.lineSeparator();
    }
}
