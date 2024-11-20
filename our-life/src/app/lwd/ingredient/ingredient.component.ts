import { Component, Input } from '@angular/core';
import { Ingredient } from '../pojo/ingredient';
import {MatCardModule} from '@angular/material/card';

@Component({
    selector: 'app-ingredient',
    imports: [MatCardModule],
    templateUrl: './ingredient.component.html',
    styleUrl: './ingredient.component.scss'
})
export class IngredientComponent {
  @Input() ingredient?:Ingredient
}
