package com.avasthi.varahamihir.common.pojos;

public class NutritionalUnits {

    public enum Mass {

        MICROGRAM("Microgram", "mcg.", 1000*1000, UNIT_SYSTEMS.METRIC),
        MILLIGRAM("Milligram", "mg.", 1000, UNIT_SYSTEMS.METRIC),
        GRAM("Gram", "g.", 1, UNIT_SYSTEMS.METRIC),
        KILOGRAM("Kilogram", "Kg.", 1e-3, UNIT_SYSTEMS.METRIC),
        OUNCE("Ounce", "oz.", 28.35, UNIT_SYSTEMS.IMPERIAL),
        POUND("Pound", "lb", 16*28.35, UNIT_SYSTEMS.IMPERIAL);
        Mass(String value,
                         String acronym,
                         double conversionToGrams,
                         UNIT_SYSTEMS unitSystems) {

            this.value = value;
            this.acronym = acronym;
            this.conversionToGrams = conversionToGrams;
            this.unitSystems = unitSystems;
            this.quantityType = QUANTITY_TYPE.MASS;
        }

        public String getValue() {
            return value;
        }

        public String getAcronym() {
            return acronym;
        }

        public double getConversionToGrams() {
            return conversionToGrams;
        }

        public UNIT_SYSTEMS getUnitSystems() {
            return unitSystems;
        }

        public QUANTITY_TYPE getQuantityType() {
            return quantityType;
        }

        private final String value;
        private final String acronym;
        private final double conversionToGrams;
        private final UNIT_SYSTEMS unitSystems;
        private final QUANTITY_TYPE quantityType;
    }

    public enum Volume {

        MILLILITER("Milliliter", "ml.", 1, UNIT_SYSTEMS.METRIC),
        DECILITER("Deciliter", "dl.", 100, UNIT_SYSTEMS.METRIC),
        LITER("Liter", "l.", 1000, UNIT_SYSTEMS.METRIC),
        TEASPOON("Teaspoon", "tsp.", 5, UNIT_SYSTEMS.IMPERIAL),
        TABLESPOON("Tablespoon", "tbsp.", 3*5, UNIT_SYSTEMS.IMPERIAL),
        FLUID_OUNCE("Fluid Ounce", "fl oz.", 2*3*5, UNIT_SYSTEMS.IMPERIAL),
        CUP("Cup", "c.", 8*2*3*5, UNIT_SYSTEMS.IMPERIAL),
        PINT("Pint", "pt.", 2*8*2*3*5, UNIT_SYSTEMS.IMPERIAL),
        QUART("Quart", "qt.", 4*8*2*3*5, UNIT_SYSTEMS.IMPERIAL),
        GALLON("Gallon", "gal.", 4*4*8*2*3*5, UNIT_SYSTEMS.IMPERIAL);
        Volume(String value,
             String acronym,
             double conversionToMillis,
             UNIT_SYSTEMS unitSystems) {

            this.value = value;
            this.acronym = acronym;
            this.conversionToMillis = conversionToMillis;
            this.unitSystems = unitSystems;
            this.quantityType = QUANTITY_TYPE.VOLUME;
        }

        public String getValue() {
            return value;
        }

        public String getAcronym() {
            return acronym;
        }

        public double getConversionToMillis() {
            return conversionToMillis;
        }

        public UNIT_SYSTEMS getUnitSystems() {
            return unitSystems;
        }

        public QUANTITY_TYPE getQuantityType() {
            return quantityType;
        }

        private final String value;
        private final String acronym;
        private final double conversionToMillis;
        private final UNIT_SYSTEMS unitSystems;
        private final QUANTITY_TYPE quantityType;
    }
}
