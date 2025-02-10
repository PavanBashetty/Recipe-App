package com.example.backend_recipe.service;

import com.example.backend_recipe.exceptions.CustomerNotFoundException;
import com.example.backend_recipe.exceptions.EmailAlreadyExistsException;
import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
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

    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }
}
