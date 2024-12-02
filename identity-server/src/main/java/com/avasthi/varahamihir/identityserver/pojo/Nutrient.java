package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;

public record Nutrient(String name, String printableName, double quantity, NutritionalUnits unit) {
}
