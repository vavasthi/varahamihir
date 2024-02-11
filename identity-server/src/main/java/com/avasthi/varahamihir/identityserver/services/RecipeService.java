package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.entities.Role;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.repositories.RecipeRepository;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Optional<Recipe> findOne(UUID id) {
        return recipeRepository.findById(id);
    }

    public Page<Recipe> findAll(int page, int size) {
        return recipeRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Recipe> save(Recipe recipe) {
        recipe = recipeRepository.save(recipe);
        return Optional.of(recipe);
    }
}
