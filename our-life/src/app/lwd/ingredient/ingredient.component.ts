import { Component, Input } from '@angular/core';
import { Ingredient } from '../pojo/ingredient';
import {MatCardModule} from '@angular/material/card';
import {IngredientWithQuantity} from "../pojo/ingredient-with-quantity";
import {UnitService} from "../services/unit.service";

@Component({
    selector: 'app-ingredient',
    imports: [MatCardModule],
    templateUrl: './ingredient.component.html',
    styleUrl: './ingredient.component.scss'
})
export class IngredientComponent {
  @Input() ingredientWithQuantity?:IngredientWithQuantity
  constructor(public unitservice:UnitService) {
  }
}
