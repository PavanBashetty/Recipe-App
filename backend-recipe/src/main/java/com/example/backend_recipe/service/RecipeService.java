package com.example.backend_recipe.service;

import com.example.backend_recipe.config.JwtUtil;
import com.example.backend_recipe.exceptions.CustomerNotFoundException;
import com.example.backend_recipe.exceptions.RecipeNotFoundException;
import com.example.backend_recipe.exceptions.UnAuthorizedException;
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
    private final JwtUtil jwtUtil;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, CustomerRepository customerRepository, JwtUtil jwtUtil){
        this.recipeRepository = recipeRepository;
        this.customerRepository = customerRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Map<String,String>> createRecipe(Recipe recipe, Customer customer, String token){

        //email from token
        String emailFromToken = jwtUtil.extractEmail(token);
        if(!customer.getEmail().equals(emailFromToken)){
            throw new UnAuthorizedException("You are not allowed to perform this action for this customer!!");
        }

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
        response.put("message","New recipe added by " + customer.getFullName());
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    public ResponseEntity<RecipeDTO> findRecipeById(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

        RecipeDTO recipeDTO = new RecipeDTO(recipe.getId(), recipe.getTitle(),recipe.getDescription(), recipe.getLikes(), recipe.getImage(), recipe.isVeg());
        return ResponseEntity.ok(recipeDTO);
    }

    public ResponseEntity<Map<String,String>> deleteRecipe(Long recipeId, String token){

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

        //Email from token
        String emailFromToken = jwtUtil.extractEmail(token);
        if(!recipe.getCustomer().getEmail().equals(emailFromToken)){
            throw new UnAuthorizedException("You can only delete your own recipes");
        }

        recipeRepository.deleteById(recipeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", " Recipe " + recipe.getTitle() +  " deleted");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RecipeDTO> updateRecipe(Recipe recipe, Long recipeId, String token){
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(()->new RecipeNotFoundException("Recipe with " + recipeId + " not found"));

        //Email from token
        String emailFromToken = jwtUtil.extractEmail(token);
        if(!existingRecipe.getCustomer().getEmail().equals(emailFromToken)){
            throw new UnAuthorizedException("You can only update your own recipes!");
        }

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

        RecipeDTO recipeDTO = new RecipeDTO(existingRecipe.getId(),existingRecipe.getTitle(),existingRecipe.getDescription(),existingRecipe.getLikes(), recipe.getImage(), recipe.isVeg());

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
                        recipe.getLikes(),
                        recipe.getImage(),
                        recipe.isVeg()
                )).toList();
        return ResponseEntity.ok(recipeDTOS);
    }

    public ResponseEntity<List<RecipeDTO>> getAllRecipesByCustomerId(Long customerId, String token) {
       Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer with " + customerId + " not found"));

       //Email from token
       String emailFromToken = jwtUtil.extractEmail(token);
       if(!customer.getEmail().equals(emailFromToken)){
            throw new UnAuthorizedException("You only view your own recipes");
       }
        List<Recipe> recipes = recipeRepository.findAllByCustomerId(customerId);
        if(recipes.isEmpty()){
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getDescription(),
                        recipe.getLikes(),
                        recipe.getImage(),
                        recipe.isVeg()
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
                recipe.getLikes(),
                recipe.getImage(),
                recipe.isVeg()
        );

        return ResponseEntity.ok(recipeDTO);
    }


}
