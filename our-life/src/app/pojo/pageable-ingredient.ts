import { Ingredient } from "./ingredient";

export interface PageableIngredient {
    content:Ingredient[];
    empty:boolean;
    first:boolean;
    last:boolean;
    size:number
}
