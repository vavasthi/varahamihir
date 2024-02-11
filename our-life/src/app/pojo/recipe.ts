import { Ingredient } from "./ingredient";
import { Nutrition } from "./nutrition";
import { Step } from "./step";
import { SugarReading } from "./sugar-reading";

export interface Recipe {
    id:string,
    name:string;
    description?:string;
    url:string;
    ingredients:Ingredient[];
    preparation:Step[];
    cooking:Step[];
    sugarReadings: SugarReading[];
    nutrition:Nutrition;
}
