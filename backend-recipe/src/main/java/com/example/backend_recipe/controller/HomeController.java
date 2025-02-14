package com.example.backend_recipe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200/")
public class HomeController {


    @GetMapping("/")
    public String greet(){
        return "Welcome to RecipeApp";
    }

    //@PutMapping()
    //@PostMapping()
    //@DeleteMapping()
}
