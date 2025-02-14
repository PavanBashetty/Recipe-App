package com.example.backend_recipe.model;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private List<Long> likes = new ArrayList<>();

    public RecipeDTO(Long id, String title, String description, List<Long> likes, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.image =image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getLikes() {
        return likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
