package com.example.backend_recipe.controller;

import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.Recipe;
import com.example.backend_recipe.model.RecipeDTO;
import com.example.backend_recipe.service.CustomerService;
import com.example.backend_recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final CustomerService customerService;

    @Autowired
    public RecipeController(RecipeService recipeService, CustomerService customerService){
        this.recipeService = recipeService;
        this.customerService = customerService;
    }

    //works
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Map<String,String>> createRecipe(@RequestBody Recipe recipe, @PathVariable Long customerId){
        Customer customer = customerService.findCustomerById(customerId);
        return recipeService.createRecipe(recipe,customer);
    }

    //works
    @GetMapping("/all")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    //works
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> findRecipeById(@PathVariable Long recipeId) {
        return recipeService.findRecipeById(recipeId);
    }

    //works
    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody Recipe recipe, @PathVariable Long recipeId){
        return recipeService.updateRecipe(recipe,recipeId);
    }

    //Works
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long recipeId){
        return recipeService.deleteRecipe(recipeId);
    }

    //works
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RecipeDTO>> getAllRecipesByCustomerId(@PathVariable Long customerId){
        return recipeService.getAllRecipesByCustomerId(customerId);
    }

    //works
    @PutMapping("/{recipeId}/customer/{customerId}")
    public ResponseEntity<RecipeDTO> likeRecipe(@PathVariable Long recipeId, @PathVariable Long customerId){
        return recipeService.likeRecipe(recipeId,customerId);
    }

}
