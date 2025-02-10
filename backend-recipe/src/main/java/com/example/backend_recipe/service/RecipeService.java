package com.example.backend_recipe.service;

import com.example.backend_recipe.exceptions.RecipeNotFoundException;
import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.Recipe;
import com.example.backend_recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<Map<String,String>> createRecipe(Recipe recipe, Customer customer){
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(recipe.getTitle());
        newRecipe.setDescription(recipe.getDescription());
        newRecipe.setCreatedAt(LocalDateTime.now());
        newRecipe.setImage(recipe.getImage());
        newRecipe.setLikes(new ArrayList<>());
        newRecipe.setVeg(recipe.isVeg());
        newRecipe.setCustomer(customer);

        recipeRepository.save(newRecipe);
        Map<String,String> response = new HashMap<>();
        response.put("message","New recipe added for " + customer.getFullName());
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    public ResponseEntity<Recipe> findRecipeById(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

        return ResponseEntity.ok(recipe);
    }

    public ResponseEntity<String> deleteRecipe(Long recipeId){}

    public ResponseEntity<Recipe> updateRecipe(Recipe recipe, Long recipeId){}

    public ResponseEntity<List<Recipe>> getAllRecipes(Long customerId){}

    public ResponseEntity<Recipe> likeRecipe(Long recipeId, Long customerId){}

}
