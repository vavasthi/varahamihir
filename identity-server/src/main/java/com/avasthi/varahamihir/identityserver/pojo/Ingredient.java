package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;

import java.util.UUID;

public record Ingredient(UUID id,
                         String name,
                         String description,
                         String brand,
                         String url,
                         String[] tags,
                         UnitType unitType,
                         UnitConversion conversion,
                         float quantity,
                         NutritionalUnits unit,
                         float calories,
                         float fatGms,
                         Float saturatedFatGms,
                         Float transFatGms,
                         Float polySaturatedFatGms,
                         Float monoUnaturatedFatGms,
                         Float cholestrolMg,
                         float carbohydratesGms,
                         Float dietaryFiberGms,
                         Float sugarsGms,
                         Float protein,
                         Float sodiumMgs,
                         Float calciumMgs,
                         Float potassiumMgs,
                         Float ironMgs,
                         Float magnesiumMgs,
                         Float cobalaminMcgs,
                         Float vitAMgs,
                         Float vitCMgs,
                         Float vitB6Mgs,
                         Float vitDMcgs) {
}
