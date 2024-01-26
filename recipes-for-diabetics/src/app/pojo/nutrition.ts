import { Quantity } from "./quantity"

export class Nutrition {
    serving:Quantity;
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
    vitaminD:Quantity|null;
    calcium:Quantity|null;
    iron:Quantity|null;
    potassium:Quantity|null;
    vitaminA:Quantity|null;
    vitaminC:Quantity|null

    constructor(serving:Quantity,
        calories:number,
        totalFat:Quantity|null,
        saturatedFat:Quantity|null,
        transFat:Quantity|null,
        polyUnsaturatedFat:Quantity|null,
        monoUnsaturatedFat:Quantity|null,
        cholestrol:Quantity|null,
        sodium:Quantity|null,
        totalCarbohydrates:Quantity|null,
        dietaryFiber:Quantity|null,
        sugars:Quantity|null,
        protein:Quantity|null,
        vitaminD:Quantity|null,
        calcium:Quantity|null,
        iron:Quantity|null,
        potassium:Quantity|null,
        vitaminA:Quantity|null,
        vitaminC:Quantity|null) {
            this.serving = serving;
            this.calories = calories;
            this.totalFat = totalFat;
            this.saturatedFat = saturatedFat;
            this.transFat = transFat;
            this.polyUnsaturatedFat = polyUnsaturatedFat;
            this.monoUnsaturatedFat = monoUnsaturatedFat;
            this.cholestrol = cholestrol;
            this.sodium = sodium;
            this.totalCarbohydrates = totalCarbohydrates;
            this.dietaryFiber = dietaryFiber;
            this.sugars = sugars;
            this.protein = protein;
            this.vitaminD = vitaminD;
            this.calcium = calcium;
            this.iron = iron;
            this.potassium = potassium;
            this.vitaminA = vitaminA;
            this.vitaminC = vitaminC;

    }
}


