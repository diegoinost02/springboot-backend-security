package com.diego.springboot.app.springbootcrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diego.springboot.app.springbootcrud.entities.Role;
import com.diego.springboot.app.springbootcrud.entities.User;
import com.diego.springboot.app.springbootcrud.repositories.RoleRepository;
import com.diego.springboot.app.springbootcrud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {

        Optional<Role> optionalRoleUser = this.roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);
        // optionalRoleUser.ifPresent(role -> roles.add(role));

        if(user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = this.roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public boolean ExistByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

}
