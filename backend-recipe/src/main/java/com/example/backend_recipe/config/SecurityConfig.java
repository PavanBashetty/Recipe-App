package com.example.backend_recipe.config;

import com.example.backend_recipe.service.CustomUserDetailsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.logging.Logger;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        logger.info("Configuring security filter chain");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    configuration.setExposedHeaders(List.of("*"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        logger.info("Security filter chain configured successfully");
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        logger.info("Setting up authentication provider..");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        logger.info("Initializing BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }


}
