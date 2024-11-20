import { Component, Input } from '@angular/core';
import { Nutrition } from '../pojo/nutrition';
import {MatCardModule} from '@angular/material/card';

@Component({
    selector: 'app-nutritional-values',
    imports: [MatCardModule],
    templateUrl: './nutritional-values.component.html',
    styleUrl: './nutritional-values.component.scss'
})
export class NutritionalValuesComponent {
  @Input() name? : string
  @Input() nutrition?: Nutrition;
}
