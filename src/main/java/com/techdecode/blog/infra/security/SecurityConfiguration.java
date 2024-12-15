package com.techdecode.blog.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.GET, "post").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "auth").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "comment").authenticated();
                    authorize.requestMatchers(HttpMethod.DELETE, "comment").authenticated();
                    authorize.anyRequest().hasRole("admin");
                })
                .build();
    }
}
