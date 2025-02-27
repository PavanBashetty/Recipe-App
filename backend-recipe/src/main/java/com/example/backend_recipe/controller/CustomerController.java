package com.example.backend_recipe.controller;

import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.CustomerDTO;
import com.example.backend_recipe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//Have this accessed via admin
@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

//    @PostMapping
//    public ResponseEntity<Map<String,String>> createCustomer(@RequestBody Customer customer){
//        return customerService.createCustomer(customer);
//    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return customerService.getAllCustomers();
    }


}
