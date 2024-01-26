import { Ingredient } from "./ingredient";
import { Step } from "./step";
import { SugarReading } from "./sugar-reading";

export class Recipe {
    name:string;
    url:string;
    ingredients:Ingredient[];
    preperation:Step[];
    cooking:Step[];
    sugarReadings: SugarReading[];

    constructor(name:string, 
        url:string, 
        ingredients:Ingredient[], 
        preperation:Step[], 
        cooking:Step[],
        sugarReadings:SugarReading[]) {
            this.name = name;
            this.url = url;
            this.ingredients = ingredients;
            this.preperation = preperation;
            this.cooking = cooking;
            this.sugarReadings = sugarReadings;
    }
}
