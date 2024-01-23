package com.diego.springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diego.springboot.app.springbootcrud.entities.User;
import com.diego.springboot.app.springbootcrud.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService { // interface de spring

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> optionalUser = this.repository.findByUsername(username);
        
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("username %s no existe en la base de datos", username));
        }
        User user = optionalUser.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        // debemos escribir el nombre del paquete entero debido a que tengo importada una clase propia con el mismo nombre (User)
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true,
            true,
            true,
            authorities);
    }
}
