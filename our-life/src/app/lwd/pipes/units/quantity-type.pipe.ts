import { Pipe, PipeTransform } from '@angular/core';
import { Unit } from '../../pojo/unit';

@Pipe({
  name: 'quantityType',
  standalone: true
})
export class QuantityTypePipe implements PipeTransform {

  transform(units: Unit[]|undefined, ...passedTypes: string[]): Unit[] {
    var returnValue:Unit[] = [];
    if (units) {

      var allowedTypes:Set<string>  = new Set<string>();
      for (let pt of passedTypes) {
        allowedTypes.add(pt)
      }
      for (let unit of units) {
        if (allowedTypes.has(unit.quantityType)) {
          returnValue.push(unit)
        }
      }
      }
    return returnValue;
  }
}
