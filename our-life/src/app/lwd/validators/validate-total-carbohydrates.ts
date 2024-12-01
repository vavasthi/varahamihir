import {AbstractControl, ValidatorFn} from "@angular/forms";

export function validateTotalCarbohydrates(): ValidatorFn {
  return (control: AbstractControl) => {

    const totalCarbohydrates = control.get('totalCarbohydrates')
    const dietaryFiber = control.get('dietaryFiber')
    const sugars = control.get('sugars')
    let componentTotal = 0;
    if (dietaryFiber) {
      componentTotal += dietaryFiber.value
    }
    if (sugars) {
      componentTotal += sugars.value
    }
    console.log("Validator", componentTotal, totalCarbohydrates?.value)
    return componentTotal > totalCarbohydrates?.value ? {totalCarbohydratesLessThanItsComponents : true} : null
  }
}
