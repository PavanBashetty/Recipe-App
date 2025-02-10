package com.example.backend_recipe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {


    @GetMapping("/")
    public String greet(){
        return "Welcome to RecipeApp";
    }

    //@PutMapping()
    //@PostMapping()
    //@DeleteMapping()
}
