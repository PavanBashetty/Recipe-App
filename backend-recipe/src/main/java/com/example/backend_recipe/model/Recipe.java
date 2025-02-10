package com.example.backend_recipe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;
    private String image;
    private String description;
    private boolean veg;
    private LocalDateTime createdAt;
    @ElementCollection
    private List<Long> likes = new ArrayList<>();

    public Recipe() {}

    public Recipe(String title, Customer customer, String image, String description, boolean veg, LocalDateTime createdAt, List<Long> likes) {
        this.title = title;
        this.customer = customer;
        this.image = image;
        this.description = description;
        this.veg = veg;
        this.createdAt = createdAt;
        this.likes = likes;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVeg() {
        return veg;
    }

    public void setVeg(boolean veg) {
        this.veg = veg;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getLikes() {
        return likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }
}
