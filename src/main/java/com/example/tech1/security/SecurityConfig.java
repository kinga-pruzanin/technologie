package com.example.tech1.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuration class for defining security settings.
     */
    @Value("${jwt.token.key}")
    private String key;
    // w bazie danych role zapisuje sie ROLE_NAZWA
    // w konfiguracji role zapisuje sie NAZWA

    /**
     * Configures security filter chain for HTTP requests.
     * @param http The HttpSecurity object for configuring security.
     * @return The SecurityFilterChain object representing the configured security filter chain.
     * @throws Exception If an error occurs during security configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/book/getAll").hasAnyRole("LIBRARIAN", "READER")
                                        .requestMatchers("/book/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/book/delete/{isbn}").hasRole("LIBRARIAN")
                                        .requestMatchers("/book/update/{isbn}").hasRole("LIBRARIAN")
                                        .requestMatchers("/user/me/role").authenticated()
                                        .requestMatchers("/user/me/id").authenticated()
                                        .requestMatchers("/user/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/user/delete/{id}").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/getAll").hasAnyRole("LIBRARIAN", "READER")
                                        .requestMatchers("/loan/accept/{id}").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/return/{id}").hasRole("READER")
                                        .requestMatchers("/loan/add").hasRole("READER")
                                        .requestMatchers("/bookDetails/**").hasRole("READER")
                                        .requestMatchers("/review/**").hasRole("READER")
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("/swagger-ui/**").permitAll()
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
