package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.common.pojos.NutritionalUnits;

public record UnitConversion(Float weightQuantity,
                             NutritionalUnits weightUnit,
                             Float volumeQuantity,
                             NutritionalUnits volumeUnit) {
}
