package com.example.backend_recipe.controller;

import com.example.backend_recipe.model.Recipe;
import com.example.backend_recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> findRecipeById(@PathVariable Long recipeId) {
        return recipeService.findRecipeById(recipeId);
    }

}
