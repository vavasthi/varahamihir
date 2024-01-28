import { Quantity } from "./quantity"

export interface Nutrition {
    quantity:Quantity;
    calories:number;
    totalFat:Quantity|null;
    saturatedFat:Quantity|null;
    transFat:Quantity|null;
    polyUnsaturatedFat:Quantity|null;
    monoUnsaturatedFat:Quantity|null;
    cholestrol:Quantity|null;
    sodium:Quantity|null;
    totalCarbohydrates:Quantity|null;
    dietaryFiber:Quantity|null;
    sugars:Quantity|null;
    protein:Quantity|null;
    calcium:Quantity|null;
    potassium:Quantity|null;
    iron:Quantity|null;
    magnesium:Quantity|null;
    cobalamin:Quantity|null;
    vitaminA:Quantity|null;
    vitaminC:Quantity|null;
    vitaminB6:Quantity|null;
    vitaminD:Quantity|null;
}


