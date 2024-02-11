import { Ingredient } from "./ingredient";
import { Nutrition } from "./nutrition";

export interface IngredientWithNutrition {
    ingredient:Ingredient;
    nutrition:Nutrition;
}
