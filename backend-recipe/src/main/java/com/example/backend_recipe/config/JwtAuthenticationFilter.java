package com.example.backend_recipe.config;

import com.example.backend_recipe.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(token);
            logger.info("Email successfully extracted from token: " + email);
        }

        if(email != null & SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            if(jwtUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                logger.info("JWT token validated and authentication set for email: " + email);
            }else{
                logger.warning("Invalidate JWT token for email: " + email);
            }
        }
        filterChain.doFilter(request,response);
        logger.info("JWT Authentication Filter completed");
    }
}
