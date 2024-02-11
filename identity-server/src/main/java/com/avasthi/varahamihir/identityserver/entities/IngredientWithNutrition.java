package com.avasthi.varahamihir.identityserver.entities;

public record IngredientWithNutrition(String name, String url, Quantity quantity, Nutrition nutrition) {
}
