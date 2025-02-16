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
@CrossOrigin(origins = "http://localhost:4200/")
public class RecipeController {

    private final RecipeService recipeService;
    private final CustomerService customerService;

    @Autowired
    public RecipeController(RecipeService recipeService, CustomerService customerService){
        this.recipeService = recipeService;
        this.customerService = customerService;
    }

    //works, is getting called from frontend
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Map<String,String>> createRecipe(@RequestBody Recipe recipe, @PathVariable Long customerId, @RequestHeader("Authorization") String token){
        Customer customer = customerService.findCustomerById(customerId);
        String extractedToken = token.substring(7);
        return recipeService.createRecipe(recipe,customer, extractedToken);
    }

    //works, is getting called from frontend
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
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody Recipe recipe, @PathVariable Long recipeId, @RequestHeader("Authorization") String token){
        String extractedToken = token.substring(7);
        return recipeService.updateRecipe(recipe,recipeId, extractedToken);
    }

    //Works
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Map<String,String>> deleteRecipe(@PathVariable Long recipeId, @RequestHeader("Authorization") String token){
        String extractedToken = token.substring(7);
        return recipeService.deleteRecipe(recipeId, extractedToken);
    }

    //works, is getting called from frontend
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RecipeDTO>> getAllRecipesByCustomerId(@PathVariable Long customerId, @RequestHeader("Authorization") String token){
        String extractedToken = token.substring(7);
        return recipeService.getAllRecipesByCustomerId(customerId, extractedToken);
    }

    //works, is getting called from frontend
    @PutMapping("/{recipeId}/customer/{customerId}")
    public ResponseEntity<RecipeDTO> likeRecipe(@PathVariable Long recipeId, @PathVariable Long customerId){
        return recipeService.likeRecipe(recipeId,customerId);
    }

}
