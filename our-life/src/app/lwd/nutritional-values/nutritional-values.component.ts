import {Component, Input, Signal, signal, WritableSignal} from '@angular/core';
import {Nutrition} from '../pojo/nutrition';
import {MatCardModule} from '@angular/material/card';
import {UnitService} from "../services/unit.service";
import {Unit} from "../pojo/unit";

@Component({
  selector: 'app-nutritional-values',
  imports: [MatCardModule],
  templateUrl: './nutritional-values.component.html',
  styleUrl: './nutritional-values.component.scss'
})
export class NutritionalValuesComponent {
  @Input() name?: string
  @Input() nutrition?: Nutrition;
  units: Signal<Unit[] | undefined> = signal([])

  constructor(public unitService: UnitService) {
    this.units = this.unitService.units
  }

  getUnit(name: string|undefined): Unit {
    if (!name || name === '') {
      name = "Unknown";
    }
    return this.unitService.getUnit(name)
  }
}
