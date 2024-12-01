import { Component } from '@angular/core';
import { IngredientsComponent } from '../ingredients/ingredients.component';
import { SugarChartComponent } from '../sugar-chart/sugar-chart.component';
import {MatIconModule} from '@angular/material/icon';
import { NutritionalValuesComponent } from '../nutritional-values/nutritional-values.component';
import {MatListModule} from '@angular/material/list'
import * as storedRecipe from '../../../assets/defaultRecipe.json';
import {Recipe} from "../pojo/recipe";

@Component({
    selector: 'app-recipe',
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
