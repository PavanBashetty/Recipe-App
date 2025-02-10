package com.example.backend_recipe.model;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String fullName;
    private String email;
    private List<RecipeDTO> recipes;

    public CustomerDTO(Long id, String fullName, String email, List<RecipeDTO> recipes) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.recipes = recipes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }
}
