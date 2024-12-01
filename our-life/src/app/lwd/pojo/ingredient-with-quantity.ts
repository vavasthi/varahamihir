import {UnitConversion} from "./unit-conversion";
import {Ingredient} from "./ingredient";
import {Quantity} from "./quantity";

export interface IngredientWithQuantity {
  ingredient:Ingredient;
  quantity:Quantity;
}
