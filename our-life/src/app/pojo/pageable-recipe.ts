import { Recipe } from "./recipe";

export interface PageableRecipe {
    content:Recipe[];
    empty:boolean;
    first:boolean;
    last:boolean;
    size:number
}
