package com.aditya.loginsignupapi.config;

import com.aditya.loginsignupapi.filters.JwtRequestFilter;
import com.aditya.loginsignupapi.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService myUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(request -> request.requestMatchers("/authenticate","/signin","/signup").permitAll().anyRequest().authenticated())
                        .sessionManagement(sessionmanagement -> sessionmanagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
