import { Component, Input } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {IngredientComponent} from "../ingredient/ingredient.component";
import {Ingredient} from "../pojo/ingredient";
import {IngredientWithQuantity} from "../pojo/ingredient-with-quantity";

@Component({
    selector: 'app-ingredients',
    imports: [IngredientComponent, MatCardModule],
    templateUrl: './ingredients.component.html',
    styleUrl: './ingredients.component.scss'
})
export class IngredientsComponent {
  @Input() ingredients?:IngredientWithQuantity[];
}
