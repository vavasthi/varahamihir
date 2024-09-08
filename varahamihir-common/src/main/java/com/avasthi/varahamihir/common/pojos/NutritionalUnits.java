package com.avasthi.varahamihir.common.pojos;

public class NutritionalUnits {

    public enum Weight {

        Gram("Gram", "g.", 1, UNIT_SYSTEM.Metric, null),
        Microgram("Microgram", "mcg.", 1000 * 1000, UNIT_SYSTEM.Metric, Gram),
        Milligram("Milligram", "mg.", 1000, UNIT_SYSTEM.Metric, Gram),
        Kilogram("Kilogram", "Kg.", 1e-3, UNIT_SYSTEM.Metric, Gram),
        Ounce("Ounce", "oz.", 28.35, UNIT_SYSTEM.Imperial, Gram),
        Pound("Pound", "lb", 16 * 28.35, UNIT_SYSTEM.Imperial, Gram);

        Weight(String value,
               String acronym,
               double conversionToPrimary,
               UNIT_SYSTEM unitSystems,
               Weight primary) {

            this.value = value;
            this.acronym = acronym;
            this.conversionToPrimary = conversionToPrimary;
            this.unitSystems = unitSystems;
            this.quantityType = QUANTITY_TYPE.Weight;
            this.primary = primary;
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

        public Weight getPrimary() {
            return primary;
        }

        private final String value;
        private final String acronym;
        private final double conversionToPrimary;
        private final UNIT_SYSTEM unitSystems;
        private final QUANTITY_TYPE quantityType;
        private final Weight primary;
    }

    public enum Volume {

        Milliliter("Milliliter", "ml.", 1, UNIT_SYSTEM.Metric, null),
        Deciliter("Deciliter", "dl.", 100, UNIT_SYSTEM.Metric, Milliliter),
        Liter("Liter", "l.", 1000, UNIT_SYSTEM.Metric, Milliliter),
        Teaspoon("Teaspoon", "tsp.", 5, UNIT_SYSTEM.Imperial, Milliliter),
        Tablespoon("Tablespoon", "tbsp.", 3 * 5, UNIT_SYSTEM.Imperial, Milliliter),
        Fluid_Ounce("Fluid Ounce", "fl oz.", 2 * 3 * 5, UNIT_SYSTEM.Imperial, Milliliter),
        Cup("Cup", "c.", 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, Milliliter),
        Pint("Pint", "pt.", 2 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, Milliliter),
        Quart("Quart", "qt.", 4 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, Milliliter),
        Gallon("Gallon", "gal.", 4 * 4 * 8 * 2 * 3 * 5, UNIT_SYSTEM.Imperial, Milliliter);

        Volume(String value,
               String acronym,
               double conversionToPrimary,
               UNIT_SYSTEM unitSystems,
               Volume primary) {

            this.value = value;
            this.acronym = acronym;
            this.conversionToPrimary = conversionToPrimary;
            this.unitSystems = unitSystems;
            this.quantityType = QUANTITY_TYPE.Volume;
            this.primary = primary;
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

        public Volume getPrimary() {
            return primary;
        }

        private final String value;
        private final String acronym;
        private final double conversionToPrimary;
        private final UNIT_SYSTEM unitSystems;
        private final QUANTITY_TYPE quantityType;
        private final Volume primary;
    }
}
