import { Nutrition } from "./nutrition";
import { Quantity } from "./quantity";

export interface Ingredient {
    name:string;
    description?:string;
    url:string;
    tags?:string[]|undefined;
    quantity:Quantity;
}
