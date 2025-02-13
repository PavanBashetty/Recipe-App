package com.example.backend_recipe.exceptions;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message){
        super(message);
    }
}
