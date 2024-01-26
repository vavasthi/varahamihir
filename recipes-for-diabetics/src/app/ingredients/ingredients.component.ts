import { Component, Input } from '@angular/core';
import { IngredientComponent } from '../ingredient/ingredient.component';
import { Ingredient } from '../pojo/ingredient';

@Component({
  selector: 'app-ingredients',
  standalone: true,
  imports: [IngredientComponent],
  templateUrl: './ingredients.component.html',
  styleUrl: './ingredients.component.scss'
})
export class IngredientsComponent {
  @Input() ingredients?:Ingredient[];
}
