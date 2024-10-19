package com.avasthi.varahamihir.common.pojos;

public enum NutritionalUnits {


    Gram("Gram", "g.", 1, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Weight, null),
    Microgram("Microgram", "mcg.", 1000 * 1000, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Weight, Gram),
    Milligram("Milligram", "mg.", 1000, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Weight, Gram),
    Kilogram("Kilogram", "Kg.", 1e-3, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Weight, Gram),
    Ounce("Ounce", "oz.", 28.35, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Weight, Gram),
    Pound("Pound", "lb", 16 * 28.35, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Weight, Gram),
    Milliliter("Milliliter", "ml.", 1, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Volume, null),
    Deciliter("Deciliter", "dl.", 100, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Volume, Milliliter),
    Liter("Liter", "l.", 1000, UNIT_SYSTEM.Metric, QUANTITY_TYPE.Volume, Milliliter),
    Teaspoon("Teaspoon", "tsp.", 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Tablespoon("Tablespoon", "tbsp.", 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Fluid_Ounce("Fluid Ounce", "fl oz.", 2 * 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Cup("Cup", "c.", 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Pint("Pint", "pt.", 2 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Quart("Quart", "qt.", 4 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter),
    Gallon("Gallon", "gal.", 4 * 4 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, QUANTITY_TYPE.Volume, Milliliter);

    NutritionalUnits(String value,
                     String acronym,
                     double conversionToPrimary,
                     UNIT_SYSTEM unitSystems,
                     QUANTITY_TYPE quantityType,
                     NutritionalUnits primary) {

        this.value = value;
        this.acronym = acronym;
        this.conversionToPrimary = conversionToPrimary;
        this.unitSystems = unitSystems;
        this.quantityType = quantityType;
        this.primary = primary == null ? this : primary;
    }

    public String getValue() {
        return value;
    }

    public String getAcronym() {
        return acronym;
    }

    public double getConversionToPrimary() {
        return conversionToPrimary;
    }

    public UNIT_SYSTEM getUnitSystems() {
        return unitSystems;
    }

    public QUANTITY_TYPE getQuantityType() {
        return quantityType;
    }

    public NutritionalUnits getPrimary() {
        return primary;
    }

    private final String value;
    private final String acronym;
    private final double conversionToPrimary;
    private final UNIT_SYSTEM unitSystems;
    private final QUANTITY_TYPE quantityType;
    private final NutritionalUnits primary;

}
