import {AbstractControl, ValidatorFn} from "@angular/forms";

export function validateTotalFat(): ValidatorFn {
  return (control: AbstractControl) => {

    const totalFat = control.get('totalFat')
    const saturatedFat = control.get('saturatedFat')
    const transFat = control.get('transFat')
    const polyUnsaturatedFat = control.get('polyUnsaturatedFat')
    const monoUnsaturatedFat = control.get('monoUnsaturatedFat')
    let componentTotal = 0;
    if (saturatedFat) {
      componentTotal += saturatedFat.value
    }
    if (transFat) {
      componentTotal += transFat.value
    }
    if (polyUnsaturatedFat) {
      componentTotal += polyUnsaturatedFat.value
    }
    if (monoUnsaturatedFat) {
      componentTotal += monoUnsaturatedFat.value
    }
    console.log("Running form validator.", componentTotal)
    return componentTotal > totalFat?.value ? {totalFatLessThanItsComponents : true} : null
  }
}
