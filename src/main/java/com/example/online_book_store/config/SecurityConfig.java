package com.example.online_book_store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.online_book_store.filter.JwtFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    @Bean
    public PasswordEncoder getPasswordEncoder(){
    
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception{
        // return http
        //         .csrf(AbstractHttpConfigurer::disable)
        //         .authorizeHttpRequests(authorize -> authorize
        //         .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
        //         .requestMatchers("/admin/**").hasRole("ADMIN")
        //         .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
        //         .anyRequest()
        //         .authenticated())
        //         .formLogin(form -> form
        //         .loginPage("/login")
        //         .loginProcessingUrl("/api/auth/login")
        //         .defaultSuccessUrl("/", true)
        //         .failureUrl("/login?error=true")
        //         .permitAll())
        //         .logout(logout -> logout
        //         .logoutUrl("/api/auth/logout")
        //         .logoutSuccessUrl("/login") // Redirect after logout
        //         .permitAll())
        //         .sessionManagement(session -> session
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        //         .build();
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/auth/login", "/api/auth/register")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
                
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception{
        
        return configuration.getAuthenticationManager();
    }
}
