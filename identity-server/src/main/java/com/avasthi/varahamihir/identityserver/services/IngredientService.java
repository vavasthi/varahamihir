package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.NutritionalUnits;
import com.avasthi.varahamihir.identityserver.entities.IngredientEntity;
import com.avasthi.varahamihir.identityserver.pojo.IngredientWithNutrition;
import com.avasthi.varahamihir.identityserver.entities.Nutrition;
import com.avasthi.varahamihir.identityserver.entities.Quantity;
import com.avasthi.varahamihir.identityserver.pojo.Ingredient;
import com.avasthi.varahamihir.identityserver.repositories.IngredientRepository;
import com.avasthi.varahamihir.identityserver.repositories.NutritionRepository;
import jakarta.transaction.Transactional;
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
        IngredientEntity ingredientEntity = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Ingredient", id.toString()),
                        String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Ingredient", id.toString())));

        Nutrition nutrition = nutritionRepository.findById(ingredientEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getReason(), "Nutrition", id.toString()),
                String.format(ExceptionMessage.ENTITY_NOT_FOUND_ERROR.getError(), "Nutrition", id.toString())));
        return Optional.of(new IngredientWithNutrition(ingredientEntity, nutrition));
    }

    public Page<IngredientEntity> findAll(int page, int size) {
        return ingredientRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Optional<IngredientWithNutrition> save(IngredientWithNutrition ingredientWithNutrition) {
        IngredientEntity ingredientEntity
                = IngredientEntity.builder()
                .url(ingredientWithNutrition.ingredient().getUrl())
                .brand(ingredientWithNutrition.ingredient().getBrand())
                .description(ingredientWithNutrition.ingredient().getDescription())
                .name(ingredientWithNutrition.ingredient().getName())
                .tags(ingredientWithNutrition.ingredient().getTags())
                .unitType(ingredientWithNutrition.ingredient().getUnitType())
                .unitConversion(ingredientWithNutrition.ingredient().getUnitConversion())
                .build();
        ingredientEntity = ingredientRepository.save(ingredientEntity);
        ingredientWithNutrition.nutrition().setId(ingredientEntity.getId());
        Nutrition nutrition = nutritionRepository.save(ingredientWithNutrition.nutrition());
        return Optional.of(new IngredientWithNutrition(ingredientEntity,
                nutrition));
    }
}
