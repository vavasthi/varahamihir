import { Nutrition } from "./nutrition";
import { Quantity } from "./quantity";

export interface Ingredient {
    name:string;
    url:string;
    quantity:Quantity;
}
