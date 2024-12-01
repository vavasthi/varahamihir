package com.avasthi.varahamihir.identityserver.entities;

import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Nutrition extends AbstractBase{
    private Quantity quantity;
    private float calories;
    private Quantity totalFat;
    private Quantity saturatedFat;
    private Quantity transFat;
    private Quantity polyUnsaturatedFat;
    private Quantity monoUnsaturatedFat;
    private Quantity cholesterol;
    private Quantity sodium;
    private Quantity totalCarbohydrates;
    private Quantity dietaryFiber;
    private Quantity sugars;
    private Quantity protein;
    private Quantity calcium;
    private Quantity potassium;
    private Quantity iron;
    private Quantity magnesium;
    private Quantity cobalamin;
    private Quantity vitaminA;
    private Quantity vitaminC;
    private Quantity vitaminB6;
    private Quantity vitaminD;
    UnitType unitType;
}
