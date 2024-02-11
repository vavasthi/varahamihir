package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.identityserver.entities.Ingredient;
import com.avasthi.varahamihir.identityserver.entities.IngredientWithNutrition;
import com.avasthi.varahamihir.identityserver.entities.Nutrition;
import com.avasthi.varahamihir.identityserver.repositories.IngredientRepository;
import com.avasthi.varahamihir.identityserver.repositories.NutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final NutritionRepository nutritionRepository;

    public Optional<IngredientWithNutrition> findOne(UUID id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Ingredient", id.toString()),
                        String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Ingredient", id.toString())));

        Nutrition nutrition = nutritionRepository.findByItemId(ingredient.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Nutrition", id.toString()),
                String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Nutrition", id.toString())));
        return Optional.of(new IngredientWithNutrition(ingredient.getName(), ingredient.getUrl(), ingredient.getQuantity(), nutrition));
    }

    public Page<Ingredient> findAll(int page, int size) {
        return ingredientRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Ingredient> save(Ingredient ingredient) {
        ingredient = ingredientRepository.save(ingredient);
        return Optional.of(ingredient);
    }
}
