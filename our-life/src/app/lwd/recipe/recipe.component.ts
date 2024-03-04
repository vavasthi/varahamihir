import { Component } from '@angular/core';
import { Recipe } from '../pojo/recipe';
import { Quantity } from '../pojo/quantity';
import { Unit } from '../pojo/unit';
import { IngredientsComponent } from '../ingredients/ingredients.component';
import { SugarReading } from '../pojo/sugar-reading';
import { SugarChartComponent } from '../sugar-chart/sugar-chart.component';
import {MatIconModule} from '@angular/material/icon';
import { NutritionalValuesComponent } from '../nutritional-values/nutritional-values.component';
import {MatListModule} from '@angular/material/list'
import * as storedRecipe from '../../../assets/defaultRecipe.json';

@Component({
  selector: 'app-recipe',
  standalone: true,
  imports: [IngredientsComponent, 
    SugarChartComponent,
    NutritionalValuesComponent, 
    MatIconModule,
  MatListModule],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.scss'
})
export class RecipeComponent {

  recipe:Recipe = storedRecipe;
  constructor() {
    
  }
}
