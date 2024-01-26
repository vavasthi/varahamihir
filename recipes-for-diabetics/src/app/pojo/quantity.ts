import { Unit } from "./unit";

export class Quantity {
    value:number;
    unit:Unit;

    constructor(value:number, unit:Unit) {
        this.value = value;
        this.unit = unit;
    }
}
