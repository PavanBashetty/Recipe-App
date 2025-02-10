package com.example.backend_recipe.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String message){
        super(message);
    }
}
