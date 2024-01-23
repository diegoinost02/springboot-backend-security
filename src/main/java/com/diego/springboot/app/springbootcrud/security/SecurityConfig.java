package com.diego.springboot.app.springbootcrud.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.diego.springboot.app.springbootcrud.security.filter.JwtAuthenticationFilter;
import com.diego.springboot.app.springbootcrud.security.filter.JwtValidationFilter;

@Configuration
// @EnableMethodSecurity(prePostEnabled = true) para habilitar el uso de @PreAuthorize
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( (authz) -> authz

        .requestMatchers(HttpMethod.GET, "/api/users").permitAll() // obtener users
        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll() // registrarse (user)
        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN") // crear users (users y admins)

        .requestMatchers(HttpMethod.GET, "/api/products", "api/products/{id}").hasAnyRole("ADMIN", "USER") // obtener productos
        .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN") // crear productos
        .requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("ADMIN") // modificar productos
        .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMIN") // eliminar productos

        .anyRequest().authenticated())

        .addFilter(new JwtAuthenticationFilter(this.authenticationManager())) // nuestra configuración
        .addFilter(new JwtValidationFilter(this.authenticationManager())) // nuestra configuración
        .csrf(config -> config.disable())
        // .cors(cors -> cors.configurationSource(corsConfigurationSource())) configurar el course
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }

    // configuracion de course
    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
    //     // config.setAllowedOriginsPatterns(Arrays.asList("*");
    //     config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    //     config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    //     config.setAllowCredentials(true);

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
    // }

    // @Bean
    // FilterRegistrationBean<CorsFilter> corsFilter() {
    //     FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
    //     corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    //     return corsBean;
    // }
} 