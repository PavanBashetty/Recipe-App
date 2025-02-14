package com.example.backend_recipe.service;

import com.example.backend_recipe.config.JwtUtil;
import com.example.backend_recipe.exceptions.CustomerNotFoundException;
import com.example.backend_recipe.exceptions.EmailAlreadyExistsException;
import com.example.backend_recipe.model.AuthLoginRequest;
import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.Role;
import com.example.backend_recipe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomerRepository customerRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<Map<String, String>> register(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists. Try a different email");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.USER);
        customerRepository.save(customer);
        Map<String,String> response = new HashMap<>();
        response.put("message","Customer registered successfully!");
        return ResponseEntity.ok(response);
    }

    public String login(AuthLoginRequest authLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginRequest.getEmail(), authLoginRequest.getPassword()));

        Customer customer = customerRepository.findByEmail(authLoginRequest.getEmail())
                .orElseThrow(()->new CustomerNotFoundException("Customer not found with email " + authLoginRequest.getEmail()));

        return jwtUtil.generateToken(authLoginRequest.getEmail(), customer.getId());
    }
}
