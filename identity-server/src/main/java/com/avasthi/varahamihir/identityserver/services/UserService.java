package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.identityserver.entities.Role;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.utils.Constants;
import com.mongodb.MongoWriteException;
import jakarta.annotation.PostConstruct;
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
        List<User> users = userRepository.findAll();
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    public Optional<User> save(User user) {
        if (userRepository.findByUsernameOrEmail(user.getUsername()).isPresent()) {

            throw new EntityAlreadyExistsException(String.format(ExceptionMessage.USER_ALREADY_EXISTS.getReason(), user.getUsername()),
                    String.format(ExceptionMessage.USER_ALREADY_EXISTS.getError(), user.getUsername()));
        }
        if (userRepository.findByUsernameOrEmail(user.getEmail()).isPresent()) {

            throw new EntityAlreadyExistsException(String.format(ExceptionMessage.EMAIL_ALREADY_EXISTS.getReason(), user.getEmail()),
                    String.format(ExceptionMessage.USER_ALREADY_EXISTS.getError(), user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setGrantedAuthorities(Set.of(Role.USER));
        user.setCreatedAt(new Date());
        user.setCreatedBy(user.getUsername());
        user.setUpdatedBy(user.getUsername());
        return Optional.of(userRepository.save(user));
    }
}
