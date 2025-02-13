package com.example.backend_recipe.service;

import com.example.backend_recipe.model.Customer;
import com.example.backend_recipe.model.Role;
import com.example.backend_recipe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    //I am treating this username as an email, because the authentication is based on the customer's email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Customer not found with email: " + email));

        return new User(customer.getEmail(), customer.getPassword(), getAuthorities(customer.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
