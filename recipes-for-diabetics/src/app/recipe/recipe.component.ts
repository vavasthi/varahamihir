import { Component } from '@angular/core';
import { Recipe } from '../pojo/recipe';
import { Ingredient } from '../pojo/ingredient';
import { Nutrition } from '../pojo/nutrition';
import { Quantity } from '../pojo/quantity';
import { Unit } from '../pojo/unit';
import { IngredientsComponent } from '../ingredients/ingredients.component';
import { SugarReading } from '../pojo/sugar-reading';
import { SugarChartComponent } from '../sugar-chart/sugar-chart.component';

@Component({
  selector: 'app-recipe',
  standalone: true,
  imports: [IngredientsComponent, SugarChartComponent],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.scss'
})
export class RecipeComponent {

  recipe:Recipe;
  constructor() {
    
    this.recipe = new Recipe("My First Recipe",
    "https://thumbs.dreamstime.com/z/indian-main-course-paneer-butter-masala-punjabi-curry-109156136.jpg?ct=jpeg", 
    [new Ingredient("Jeera", "https://as1.ftcdn.net/v2/jpg/01/45/64/60/1000_F_145646010_VvQuqzNWcySHfqxhjEwo2fd21v996IbS.jpg", new Nutrition(new Quantity(1, new Unit("Grams", "gm")), 100, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), new Quantity(10, new Unit("Grams", "gm"))),
    new Ingredient("Haldi", "https://as1.ftcdn.net/v2/jpg/01/82/27/42/1000_F_182274289_RvpPTYZmC3n98ZXuH85d31XBfyEhk6b1.jpg", new Nutrition(new Quantity(1, new Unit("Grams", "gm")), 100, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), new Quantity(10, new Unit("Grams", "gm")))
    ], 
    [], 
    [],
    [
      new SugarReading(0,80),
      new SugarReading(15,90),
      new SugarReading(30,120),
      new SugarReading(45,140),
      new SugarReading(60,150),
      new SugarReading(75,140),
      new SugarReading(90,120),
      new SugarReading(105,90),
      new SugarReading(115, 70)
    ]);
  }
}
