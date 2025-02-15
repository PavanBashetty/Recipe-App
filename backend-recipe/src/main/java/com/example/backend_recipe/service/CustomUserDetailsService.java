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
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private CustomerRepository customerRepository;


    //I am treating this username as an email, because the authentication is based on the customer's email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by email: " + email);
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()->{
                    logger.warning("Customer not found with email: " + email);
                    return new UsernameNotFoundException("Customer not found with email: " + email);
                });
        logger.info("Customer found with email: " + email);
        return new User(customer.getEmail(), customer.getPassword(), getAuthorities(customer.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role){
        logger.info("Assigning role: " + role);
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
