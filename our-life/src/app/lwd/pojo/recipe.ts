import { Nutrition } from "./nutrition";
import { Step } from "./step";
import { SugarReading } from "./sugar-reading";
import {Ingredient} from "./ingredient";
import {IngredientWithQuantity} from "./ingredient-with-quantity";

export interface Recipe {
    id:string,
    name:string;
    description?:string;
    url:string;
    ingredients:IngredientWithQuantity[];
    preparation:Step[];
    cooking:Step[];
    sugarReadings: SugarReading[];
    nutrition:Nutrition;
}
