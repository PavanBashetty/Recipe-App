package com.example.backend_recipe.service;

import com.example.backend_recipe.exceptions.CustomerNotFoundException;
import com.example.backend_recipe.exceptions.EmailAlreadyExistsException;
import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.CustomerDTO;
import com.example.backend_recipe.model.RecipeDTO;
import com.example.backend_recipe.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(Long customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer with id " + customerId + " not found"));
    }

    public ResponseEntity<Map<String, String>> createCustomer(Customer customer){
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists. Try a different email");
        }
        customerRepository.save(customer);
        Map<String,String> response = new HashMap<>();
        response.put("message","new customer created!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> deleteCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            customerRepository.deleteById(customerId);
            return ResponseEntity.ok("Customer account deleted");
        }else{
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }
    }

    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getRecipes().stream()
                                .map(recipe -> new RecipeDTO(
                                        recipe.getId(),
                                        recipe.getTitle(),
                                        recipe.getDescription(),
                                        recipe.getLikes(),
                                        recipe.getImage(),
                                        recipe.isVeg()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOS);
    }
}
