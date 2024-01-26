import { Nutrition } from "./nutrition";
import { Quantity } from "./quantity";

export class Ingredient {
    name:string;
    url:string;
    nutrition:Nutrition;
    quantity:Quantity
    constructor(name:string, url:string, nutrition:Nutrition, quantity:Quantity) {
        this.name = name;
        this.url = url;
        this.nutrition = nutrition;
        this.quantity = quantity;
    }
}
