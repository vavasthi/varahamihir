package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.identityserver.entities.IngredientEntity;
import com.avasthi.varahamihir.identityserver.entities.Nutrition;


public record IngredientWithNutrition(IngredientEntity ingredient, Nutrition nutrition) {
}
