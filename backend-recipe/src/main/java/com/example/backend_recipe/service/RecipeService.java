package com.example.backend_recipe.service;

import com.example.backend_recipe.exceptions.CustomerNotFoundException;
import com.example.backend_recipe.exceptions.RecipeNotFoundException;
import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.Recipe;
import com.example.backend_recipe.model.RecipeDTO;
import com.example.backend_recipe.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, CustomerRepository customerRepository){
        this.recipeRepository = recipeRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<Map<String,String>> createRecipe(Recipe recipe, Customer customer){

        Recipe newRecipe = new  Recipe();
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

    public ResponseEntity<RecipeDTO> findRecipeById(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

        RecipeDTO recipeDTO = new RecipeDTO(recipe.getId(), recipe.getTitle(),recipe.getDescription(), recipe.getLikes());
        return ResponseEntity.ok(recipeDTO);
    }

    public ResponseEntity<String> deleteRecipe(Long recipeId){
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isPresent()){
            recipeRepository.deleteById(recipeId);
            return ResponseEntity.ok("Recipe deleted");
        }else{
            throw new RecipeNotFoundException("Recipe with " + recipeId + " not found");
        }
    }

    public ResponseEntity<RecipeDTO> updateRecipe(Recipe recipe, Long recipeId){
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

//        if(recipe.getTitle() != null) existingRecipe.setTitle(recipe.getTitle());
//        if(recipe.getImage() !=null) existingRecipe.setImage(recipe.getImage());
//        if(recipe.getDescription() !=null) existingRecipe.setDescription(recipe.getDescription());
//        recipeRepository.save(existingRecipe);
//        return ResponseEntity.ok(existingRecipe);

        Optional.ofNullable(recipe.getTitle()).ifPresent(existingRecipe::setTitle);
        Optional.ofNullable(recipe.getImage()).ifPresent(existingRecipe::setImage);
        Optional.ofNullable(recipe.getDescription()).ifPresent(existingRecipe::setDescription);
        Optional.of(recipe.isVeg()).ifPresent(existingRecipe::setVeg);
        recipeRepository.save(existingRecipe);

        RecipeDTO recipeDTO = new RecipeDTO(existingRecipe.getId(),existingRecipe.getTitle(),existingRecipe.getDescription(),existingRecipe.getLikes());

        return ResponseEntity.ok(recipeDTO);
    }

    public ResponseEntity<List<RecipeDTO>> getAllRecipes(){
        List<Recipe> recipes = recipeRepository.findAll();
        if(recipes.isEmpty()){
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getDescription(),
                        recipe.getLikes()
                )).toList();
        return ResponseEntity.ok(recipeDTOS);
    }

    public ResponseEntity<List<RecipeDTO>> getAllRecipesByCustomerId(Long customerId) {
       customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer with " + customerId + " not found"));

        List<Recipe> recipes = recipeRepository.findAllByCustomerId(customerId);
        if(recipes.isEmpty()){
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getDescription(),
                        recipe.getLikes()
                )).toList();
        return ResponseEntity.ok(recipeDTOS);
    };

    public ResponseEntity<RecipeDTO> likeRecipe(Long recipeId, Long customerId){

        customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer with id "+ customerId + " not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new RecipeNotFoundException("Recipe with id " + recipeId + " not found"));

        // Toggle the like (add if not already liked, remove if already liked)
        if(recipe.getLikes().contains(customerId)){
            recipe.getLikes().remove(customerId);
        }else{
            recipe.getLikes().add(customerId);
        }

        // Save the updated recipe back to the database
        recipeRepository.save(recipe);

        RecipeDTO recipeDTO = new RecipeDTO(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getLikes()
        );

        return ResponseEntity.ok(recipeDTO);
    }


}
