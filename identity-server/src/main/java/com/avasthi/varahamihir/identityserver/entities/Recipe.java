package com.avasthi.varahamihir.identityserver.entities;

import java.util.List;
import java.util.UUID;

public record Recipe(UUID id,
                     String name,
                     String description,
                     String url,
                     List<IngredientEntity> ingredients,
                     List<Step> preparation,
                     List<Step> cooking,
                     List<SugarReading> sugarReadings,
                     Nutrition nutrition) {
}
