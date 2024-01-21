package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.identityserver.entities.Role;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findOne(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> save(User user) {
        if (userRepository.findByUsernameOrEmail(user.getUsername()).isPresent()) {

            throw new EntityAlreadyExistsException(String.format("This error occurs when you are trying to create a new user with same username as an existing user. It looks like a user with username %s already exists", user.getUsername()),
                    String.format("Username %s already exists", user.getUsername()));
        }
        if (userRepository.findByUsernameOrEmail(user.getEmail()).isPresent()) {

            throw new EntityAlreadyExistsException(String.format("This error occurs when you are trying to create a new user with same email as an existing user. It looks like a user with email %s already exists", user.getEmail()),
                    String.format("Email %s already exists", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setGrantedAuthorities(Set.of(Role.USER));
        user.setCreatedAt(new Date());
        user.setCreatedBy(user.getUsername());
        user.setUpdatedBy(user.getUsername());
        return Optional.of(userRepository.save(user));
    }
}
