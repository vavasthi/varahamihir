package com.avasthi.varahamihir.common.pojos;

public enum RDI {

    Fat("Fat", 78, NutritionalUnits.Gram),
    SaturatedFat("Saturated Fat", 20, NutritionalUnits.Gram),
    Cholesterol("Cholesterol", 300, NutritionalUnits.Milligram),
    TotalCarbohydrates("Total Carbohydrates", 275, NutritionalUnits.Gram),
    Sodium("Sodium", 2300, NutritionalUnits.Milligram),
    DietaryFiber("Dietary Fiber", 28, NutritionalUnits.Gram),
    Protein("Protein", 50, NutritionalUnits.Gram),
    Sugars("Sugars", 50, NutritionalUnits.Gram),
    VitaminA("Vitamin A", 900, NutritionalUnits.Microgram),
    VitaminC("Vitamin C", 90, NutritionalUnits.Milligram),
    Calcium("Calcium", 1300, NutritionalUnits.Milligram),
    Iron("Iron", 18, NutritionalUnits.Milligram),
    VitaminD("Vitamin D", 20, NutritionalUnits.Microgram),
    VitaminE("Vitamin E", 15, NutritionalUnits.Milligram),
    VitaminK("Vitamin K", 120, NutritionalUnits.Microgram),
    VitaminB6("Vitamin B6", 1.7, NutritionalUnits.Milligram),
    VitaminB12("Vitamin B12", 2.4, NutritionalUnits.Microgram),
    Thiamin("Thiamin", 1.2, NutritionalUnits.Milligram),
    Riboflavin("Riboflavin", 1.3, NutritionalUnits.Milligram),
    Niacin("Niacin", 16, NutritionalUnits.Milligram),
    Folate("Folate", 300, NutritionalUnits.Microgram),
    Biotin("Biotin", 30, NutritionalUnits.Microgram),
    PantothenicAcid("Pantothenic Acid", 5, NutritionalUnits.Milligram),
    Phosphorus("Phosphorus", 1250, NutritionalUnits.Milligram),
    Iodine("Iodine", 150, NutritionalUnits.Microgram),
    Magnesium("Magnesium", 420, NutritionalUnits.Milligram),
    Zinc("Zinc", 11, NutritionalUnits.Milligram),
    Selenium("Selenium", 55, NutritionalUnits.Microgram),
    Copper("Copper", 0.9, NutritionalUnits.Milligram),
    Manganese("Manganese", 2.3, NutritionalUnits.Milligram),
    Chromium("Chromium", 35, NutritionalUnits.Microgram),
    Molybdenum("Molybdenum", 45, NutritionalUnits.Microgram),
    Chloride("Chloride", 2300, NutritionalUnits.Milligram),
    Potassium ("Potassium", 4700, NutritionalUnits.Milligram),
    Choline("Choline", 550, NutritionalUnits.Milligram);

    RDI(String nutrientName,
        double quantity,
        NutritionalUnits unit) {

        this.nutrientName = nutrientName;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public NutritionalUnits getUnit() {
        return unit;
    }

    private final String nutrientName;
    private final double quantity;
    private final NutritionalUnits unit;
}
