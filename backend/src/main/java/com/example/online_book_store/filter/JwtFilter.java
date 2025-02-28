package com.example.online_book_store.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.online_book_store.service.MyUserDetailsService;
import com.example.online_book_store.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

//        if (request.getServletPath().equals("/api/auth/login") || request.getServletPath().equals("/api/auth/register")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        if(request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().equals("/api/auth/register") || request.getRequestURI().equals("api/books/**")){
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUserName(jwt);
            } catch (ExpiredJwtException e) {
                LOGGER.error("JWT token is expired: {}", e.getMessage());
            } catch (MalformedJwtException e) {
                LOGGER.error("Invalid JWT token: {}", e.getMessage());
            } catch (Exception e) {
                LOGGER.error("Error extracting username from JWT token: {}", e.getMessage());
            }
        } else {
            LOGGER.warn("JWT token does not start with Bearer or is missing");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                LOGGER.info("Authenticated user: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}