package com.example.backend_recipe.controller;

import com.example.backend_recipe.model.Customer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer){
        return customer;
    }
}
